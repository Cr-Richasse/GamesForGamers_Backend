package com.example.GamesForGamers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Games For Gamers") // Título de tu API
                        .version("1.0") // Versión
                        .description("Documentación de los endpoints del backend para la tienda de videojuegos GFG."));
    }
}