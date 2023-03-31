package com.mastercard.mockapi.service;

import com.mastercard.mockapi.model.FieldSchema;
import com.mastercard.mockapi.model.MockPattern;
import com.mastercard.mockapi.service.generator.EnumMockGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class ObjectGenerator {
    private final MockPattern mockPattern;
    private final MockFactory mockFactory;

    private <T> Class<T> findReferenceType(String type, Class clazz) {
        var map = new HashMap<String, Class>();
        map.put("int", Integer.class);
        map.put("boolean", Boolean.class);
        map.put("byte", Byte.class);
        map.put("char", Character.class);
        map.put("double", Double.class);
        map.put("float", Float.class);
        map.put("long", Long.class);
        map.put("short", Short.class);
        return map.getOrDefault(type, clazz);
    }

    public Object generate(String path, String method) throws Exception {
        try {
            var httpPattern = this.mockPattern.getHttpPatterns()
                    .stream()
                    .filter(a -> a.getPath().equalsIgnoreCase(path) && a.getMethod().equalsIgnoreCase(method))
                    .findFirst()
                    .orElseThrow(() -> new Exception(String.format("Endpoint is not defined for %s: %s", method, path)));

            var responseType = Class.forName(httpPattern.getResponseType());
            var object= responseType.getConstructor().newInstance();
            for (var field : responseType.getDeclaredFields()) {
                field.setAccessible(true);
                this.setValue(object, field, field.getName(), httpPattern.getFieldSchemas());
            }

            return object;
        } catch (ClassNotFoundException | IllegalAccessException exception) {
            log.error(exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            throw exception;
        }
    }

    private void setValue(Object object, Field field, String fieldName, List<FieldSchema> schemas) {
        try {
            var value = this.generateValue(field, fieldName, schemas);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private Object generateValue(Field field, String fieldName, List<FieldSchema> schemas)
            throws ClassNotFoundException {
        return this.generateValue(field, false, false, fieldName, schemas);
    }

    private Object generateValue(Field field, boolean isGeneric, boolean isArray, String fieldName, List<FieldSchema> schemas)
            throws ClassNotFoundException {
        var type = isGeneric
                ? Class.forName(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName())
                : field.getType();
        type = isArray ? type.getComponentType() : type;
        var fieldType = this.fieldType(type);
        var mockGenerator = this.mockFactory.create(fieldType);
        if (mockGenerator.isPresent()) {
            var schema = findSchema(schemas, fieldName);
            schema.put("type", type);
            return mockGenerator.get().generate(schema);
        } else if (type.isArray()) {
            return this.generateArray(field, field.getType().componentType(), fieldName, schemas);
        } else if (type.equals(List.class)) {
            return this.generateList(field, fieldName, schemas);
        } else {
            return this.generateObject(field, fieldName, schemas);
        }
    }

    public Object generateObject(Field field, String fieldName, List<FieldSchema> schemas) {
        Object object = null;
        try {
            object = field.getType().getConstructor().newInstance();
            for (var f : field.getType().getDeclaredFields()) {
                f.setAccessible(true);
                var value = this.generateValue(f, String.format("%s.%s", fieldName, f.getName()), schemas);
                f.set(object, value);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return object;
    }

    private <T> List<T> generateList(Field field, String fieldName, List<FieldSchema> schemas)
            throws ClassNotFoundException {
        var schema = this.findSchema(schemas, fieldName);
        var minSize = (int) schema.getOrDefault("minSize", 0);
        var maxSize = (int) schema.getOrDefault("maxSize", 10);

        var list = new ArrayList<T>();
        for (int i = minSize; i < maxSize; i++) {
            list.add((T) this.generateValue(field, true, false, fieldName, schemas));
        }

        return list;
    }

    private <T> Object generateArray(Field field, Class<T> clazz, String fieldName, List<FieldSchema> schemas)
            throws ClassNotFoundException {
        var schema = this.findSchema(schemas, fieldName);
        var minSize = (int) schema.getOrDefault("minSize", 0);
        var maxSize = (int) schema.getOrDefault("maxSize", 10);

        var referenceType = this.findReferenceType(clazz.getName(), clazz);
        var array = (T[]) Array.newInstance(referenceType, maxSize);
        for (int i = minSize; i < maxSize; i++) {
            array[i] = (T) this.generateValue(field, false, true, fieldName, schemas);
        }

        return clazz.isPrimitive() ? ArrayUtils.toPrimitive(array) : array;
    }

    private Map<String, Object> findSchema(List<FieldSchema> schemas, String name) {
        if (schemas == null) {
            return new HashMap<>();
        }

        var schema = schemas.stream()
                .filter(a-> a.getName().equalsIgnoreCase(name))
                .map(a-> a.getSchema())
                .findFirst()
                .orElse(new HashMap<>());

        return schema;
    }

    private String fieldType(Class type) {
        var fieldType = type.getName();
        if (type.isEnum()) {
            fieldType = EnumMockGenerator.NAME;
        }

        return fieldType;
    }
}
