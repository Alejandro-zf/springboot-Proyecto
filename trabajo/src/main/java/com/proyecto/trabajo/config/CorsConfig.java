package com.proyecto.trabajo.config;

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
                                    "http://localhost:5173",
                                    "http://localhost:5174",
                                    "http://127.0.0.1:5173",
                                    "http://127.0.0.1:5174",
                                    "http://192.168.0.6:5173",
                                    "http://192.168.0.6:5174",
                                    "http://3.214.21.224:5173"
                        ) // tu frontend - agregados más puertos comunes
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
