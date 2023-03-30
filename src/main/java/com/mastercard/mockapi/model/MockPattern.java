package com.mastercard.mockapi.model;

import com.mastercard.mockapi.config.PatternPropertySourceFactory;
import com.mastercard.mockapi.config.YamlPropertySourceFactory;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
//@Configuration
@Component
@PropertySource(value = "classpath:patterns/personMock.yml", factory = YamlPropertySourceFactory.class)
//@PropertySource(value = "classpath:patterns", factory = PatternPropertySourceFactory.class)
@ConfigurationProperties("mock")
public class MockPattern {
    private List<HttpPattern> httpPatterns;
//    private HttpPattern httpPatterns;

//    private String path;
//    private String method;
//    private String requestType;
//    private String responseType;
//    private List<FieldSchema> fieldSchemas;
}
