package com.dishant.person.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class RestConfig {

    @Bean
    public OpenAPI springDocOpenAPI() {
        return new OpenAPI().info(this.apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Persons API")
                .description("API Documentation - Persons API")
                .version("1.0");
    }
}