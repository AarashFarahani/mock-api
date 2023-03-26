package com.mastercard.mockapi.config;

import com.mastercard.mockapi.service.RequestProcessor;
import com.mastercard.mockapi.servlet.MockServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServletConfig {
    private final RequestProcessor requestProcessor;

    @Bean
    public ServletRegistrationBean customServletBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new MockServlet(this.requestProcessor), "/mock");
        return bean;
    }
}
