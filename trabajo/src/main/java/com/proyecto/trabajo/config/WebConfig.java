package com.proyecto.trabajo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer configurarCors(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                // CORS para endpoints API
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
                
                // CORS para archivos estáticos (imágenes, etc.)
                registry.addMapping("/uploads/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Servir archivos estáticos desde la carpeta uploads
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:uploads/");

                // Logging para depuración de acceso a archivos
                registry.setOrder(0); // Prioridad alta
                System.out.println("[WebConfig] addResourceHandlers configurado para /uploads/**");
                java.nio.file.Path uploadsDir = java.nio.file.Paths.get("uploads/espacios");
                try {
                    if (java.nio.file.Files.exists(uploadsDir)) {
                        System.out.println("[WebConfig] uploads/espacios existe");
                        int count = 0;
                        for (java.nio.file.Path path : java.nio.file.Files.newDirectoryStream(uploadsDir)) {
                            System.out.println("[WebConfig] Archivo: " + path.getFileName() + " - Permisos: " + java.nio.file.Files.isReadable(path));
                            count++;
                            if (count >= 20) {
                                System.out.println("[WebConfig] ...más archivos no mostrados");
                                break;
                            }
                        }
                    } else {
                        System.out.println("[WebConfig] uploads/espacios NO existe");
                    }
                } catch (Exception e) {
                    System.out.println("[WebConfig] Error al listar archivos de uploads/espacios: " + e.getMessage());
                }
            }
        };
    }
}
