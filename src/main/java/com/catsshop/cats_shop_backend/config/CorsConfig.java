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
                                // Nota: No se necesitas el dominio de Render en esta lista.
                        )
                        .allowedMethods("GET","POST","PUT","DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}