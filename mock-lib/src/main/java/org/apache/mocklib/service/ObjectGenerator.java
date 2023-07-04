package org.apache.mocklib.service;

import org.apache.mocklib.model.FieldSchema;
import org.apache.mocklib.service.generator.EnumMockGenerator;
import org.apache.mocklib.service.generator.IntegerMockGenerator;
import org.apache.mocklib.utils.ReflectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
public class ObjectGenerator {
    private final MockFactory mockFactory;

    public Object generate(String className, FieldSchema... fieldSchemas) throws Exception {
        try {
            var clazz = Class.forName(className);
            return this.generate(clazz, fieldSchemas);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

    public <T> T generate(Class<T> clazz, FieldSchema... fieldSchemas) throws Exception {
        try {
            var object = clazz.getConstructor().newInstance();
            for (var field : clazz.getDeclaredFields()) {
                this.setValue(object, field, field.getName(), List.of(fieldSchemas));
            }

            return object;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }
    }

    private void setValue(Object object, Field field, String fieldName, List<FieldSchema> schemas) {
        try {
            if (!ReflectionUtils.isFinalOrStatic(field)) {
                var options = this.possibleValues(fieldName, schemas);
                Object value = null;
                if (!options.isEmpty()) {
                    var index = IntegerMockGenerator.randomInteger(0, options.size() - 1);
                    value = options.get(index);
                } else {
                    value = this.generateValue(field, fieldName, schemas);
                }

                ReflectionUtils.setValue(object, field, value);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private List<Object> possibleValues(String fieldName, List<FieldSchema> fieldSchemas) {
        var schema = this.findSchema(fieldSchemas, fieldName);
        return (List<Object>) schema.getOrDefault("options", List.of());
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
            return this.generateObject(field, isGeneric, isArray, fieldName, schemas);
        }
    }

    public Object generateObject(Field field, boolean isGeneric, boolean isArray, String fieldName, List<FieldSchema> schemas) {
        Object object = null;
        try {
            var type = isGeneric
                    ? Class.forName(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName())
                    : field.getType();
            type = isArray ? type.componentType() : type;

            object = field.getType().getConstructor().newInstance();
            for (var f : field.getType().getDeclaredFields()) {
                if (!ReflectionUtils.isFinalOrStatic(f)) {
                    var value = this.generateValue(f, String.format("%s.%s", field, f.getName()), schemas);
                    ReflectionUtils.setValue(object, f, value);
                }
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

        var referenceType = ReflectionUtils.findReferenceType(clazz.getName(), clazz);
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
