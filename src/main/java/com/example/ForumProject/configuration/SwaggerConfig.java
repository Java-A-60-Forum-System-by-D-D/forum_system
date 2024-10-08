package com.example.ForumProject.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration

public class SwaggerConfig {
    @Bean
    public OpenAPI OpenApi() {
        return new OpenAPI()
                .info(new Info().title("D&D Forum System API")
                        .version("V1")
                        .description("This is our first team project for sample ForumSystem")
                        .contact(new Contact().name("Desislava Zafirova & Dimitar Hadzhiev")
                                .url("https://github.com/Java-A-60-Forum-System-by-D-D/forum_system"))
                        .license(new License().name("MIT").url("https://github.com/Java-A-60-Forum-System-by-D-D/forum_system/blob/main/LICENSE"))
                        .termsOfService("Terms of Service"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new io.swagger.v3.oas.models.security.SecurityScheme()
                                .name("bearerAuth")
                                .description("JWT authentication")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }

}

