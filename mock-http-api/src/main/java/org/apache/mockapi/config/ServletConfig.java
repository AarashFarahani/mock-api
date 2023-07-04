package org.apache.mockapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.mockapi.service.WebObjectGenerator;
import org.apache.mockapi.utils.ConsoleUtils;
import org.apache.mockapi.servlet.MockServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServletConfig {
    private final WebObjectGenerator objectGenerator;
    private final ConsoleUtils consoleUtils;
    private final ObjectMapper objectMapper;

    @Bean
    public ServletRegistrationBean customServletBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(
                new MockServlet(this.objectGenerator, this.consoleUtils, this.objectMapper), "/mock/*");
        return bean;
    }
}
