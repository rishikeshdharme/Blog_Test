package com.example.Blog_test.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(myinfo())
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server"),
                        new Server().url("https://localhost:8082").description("Production Server")
                ))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private Info myinfo() {
        return new Info()
                .title("Blog API Documentation")
                .version("1.0.0")
                .description("Comprehensive REST API for Blog Management System\n\n" +
                        "This API allows you to:\n" +
                        "- Create, read, update, and delete blog posts\n" +
                        "- Manage user authentication and authorization\n" +
                        "- Handle comments and categories\n" +
                        "- Upload and manage media files\n\n" +
                        "**Authentication:** This API uses JWT Bearer tokens for authentication.")
                .contact(new Contact()
                        .name("Blog API Support(CodingEagle)")
                        .email("rishikeshdharme1097@.com")
                        .url("https://github.com/rishikeshdharme/Blog_Test"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Enter JWT Bearer token (obtained from /api/auth/login)");
    }
}
