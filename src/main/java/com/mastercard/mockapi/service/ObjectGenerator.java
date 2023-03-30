package com.mastercard.mockapi.service;

import com.mastercard.mockapi.model.FieldSchema;
import com.mastercard.mockapi.model.MockPattern;
import com.mastercard.mockapi.service.generator.EnumMockGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class ObjectGenerator {
    private final MockPattern mockPattern;
    private final MockFactory mockFactory;

    public Object generate(String path, String method) throws Exception {
        try {
//            var httpPattern = this.mockPattern.getHttpPatterns();
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

    private void setValue(Object object, Field field, String fieldName, List<FieldSchema> schemas)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        var fieldType = this.fieldType(field);

        var mockGenerator = this.mockFactory.create(fieldType);
        if (mockGenerator.isPresent()) {
            var schema = findSchema(schemas, fieldName);
            schema.put("type", field.getType());
            var value = mockGenerator.get().generate(schema);
            field.set(object, value);
        } else {
            var o = field.getType().getConstructor().newInstance();
            for (var f : field.getType().getDeclaredFields()) {
                f.setAccessible(true);
                this.setValue(o, f, String.format("%s.%s", fieldName, f.getName()), schemas);
            }

            field.set(object, o);
        }
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

    private String fieldType(Field field) {
        var fieldType = field.getType().getName();
        if (field.getType().isEnum()) {
            fieldType = EnumMockGenerator.NAME;
        }

        return fieldType;
    }
}
