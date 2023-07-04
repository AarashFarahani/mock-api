package org.apache.mockapi.config;

import org.apache.mocklib.service.MockFactory;
import org.apache.mocklib.service.ObjectGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public ObjectGenerator objectGenerator() {
        return new ObjectGenerator(MockFactory.getInstance());
    }
}
