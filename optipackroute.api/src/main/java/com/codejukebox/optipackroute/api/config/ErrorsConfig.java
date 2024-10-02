package com.codejukebox.optipackroute.api.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ErrorsConfig {

    @Bean
    ErrorAttributes customErrorAttributes() {
        return new ErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                return Map.of(
                    "status", 500,
                    "message", "Custom error message"
                );
            }

            @Override
            public Throwable getError(WebRequest webRequest) {
                return null;
            }
        };
    }
}

