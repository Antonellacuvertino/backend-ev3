package com.catsshop.cats_shop_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                // 1. Entorno de desarrollo local
                                "http://localhost:3000",
                                // 2. El ORIGEN CORREGIDO y actual de tu frontend en Vercel
                                "https://frontend-ev3-zbku.vercel.app"
                        )
                        // Métodos HTTP permitidos
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Cabeceras permitidas (como Content-Type y Authorization)
                        .allowedHeaders("*")
                        // **CLAVE:** Permite el envío de cookies o, más importante, la cabecera Authorization (JWT)
                        .allowCredentials(true);
            }
        };
    }
}