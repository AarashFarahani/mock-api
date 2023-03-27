package com.mastercard.mockapi.service;

import com.mastercard.mockapi.model.FieldSchema;
import com.mastercard.mockapi.model.HttpPattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ObjectGenerator {
    private final List<HttpPattern> httpPatterns;
    private final MockFactory mockFactory;

    public Object generate(String path, String method) throws Exception {
        try {
            var httpPattern = this.httpPatterns.stream()
                    .filter(a -> a.getPath().equalsIgnoreCase(path) && a.getMethod().equalsIgnoreCase(method))
                    .findFirst()
                    .orElseThrow(() -> new Exception(String.format("Endpoint is not defined for %s: %s", method, path)));

            var responseType = Class.forName(httpPattern.getResponseType());
            var object= responseType.getDeclaredConstructor().newInstance();
            for (var field : responseType.getDeclaredFields()) {
                field.setAccessible(true);
                this.setValue(object, field, httpPattern.getFieldSchemas());
            }

            return object;
        } catch (ClassNotFoundException | IllegalAccessException exception) {
            log.error(exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            throw exception;
        }
    }

    private void setValue(Object object, Field field, List<FieldSchema> schemas)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        var mockGenerator = this.mockFactory.create(field.getType().getName());
        if (mockGenerator.isPresent()) {
            var schema = schemas.stream()
                    .filter(a-> a.getName().equalsIgnoreCase(field.getName()))
                    .map(a-> a.getSchema())
                    .findFirst()
                    .orElse(new HashMap<>());
            var value = mockGenerator.get().generate(schema);
            field.set(object, value);
        } else {
            var o = field.getType().getDeclaredConstructor().newInstance();
            for (var f : field.getType().getDeclaredFields()) {
                f.setAccessible(true);
                this.setValue(o, f, schemas);
            }

            field.set(object, o);
        }
    }
}
