package org.apache.mockapi.service;

import org.apache.mockapi.model.MockPattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.mocklib.model.FieldSchema;
import org.apache.mocklib.service.ObjectGenerator;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class WebObjectGenerator {
    private final MockPattern mockPattern;
    private final ObjectGenerator objectGenerator;

    public Object generate(String path, String method) throws Exception {
        try {
            var httpPattern = this.mockPattern.getHttpPatterns()
                    .stream()
                    .filter(a -> a.getPath().equalsIgnoreCase(path) && a.getMethod().equalsIgnoreCase(method))
                    .findFirst()
                    .orElseThrow(() -> new Exception(String.format("Endpoint is not defined for %s: %s", method, path)));

            var responseType = Class.forName(httpPattern.getResponseType());
            var object= this.objectGenerator
                    .generate(responseType, httpPattern.getFieldSchemas()
                            .toArray(new FieldSchema[httpPattern.getFieldSchemas().size()]));

            return object;
        } catch (ClassNotFoundException | IllegalAccessException exception) {
            log.error(exception.getMessage());
            throw exception;
        } catch (Exception exception) {
            throw exception;
        }
    }
}
