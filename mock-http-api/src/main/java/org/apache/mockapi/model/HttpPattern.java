package org.apache.mockapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.mocklib.model.FieldSchema;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@PropertySource(value = "classpath:PersonMock.yml", factory = YamlPropertySourceFactory.class)
//@ConfigurationProperties("mock")
public class HttpPattern {
    private String path;
    private String method;
    private String requestType;
    private String responseType;
    private List<FieldSchema> fieldSchemas;
}
