package com.mastercard.mockapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.mockapi.service.ObjectGenerator;
import com.mastercard.mockapi.utils.ConsoleUtils;
import com.mastercard.mockapi.servlet.MockServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServletConfig {
    private final ObjectGenerator objectGenerator;
    private final ConsoleUtils consoleUtils;
    private final ObjectMapper objectMapper;

    @Bean
    public ServletRegistrationBean customServletBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new MockServlet(this.objectGenerator, this.consoleUtils, this.objectMapper), "/mock/*");
        return bean;
    }
}
