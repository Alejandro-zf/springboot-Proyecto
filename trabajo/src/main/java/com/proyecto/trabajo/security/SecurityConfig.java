package com.proyecto.trabajo.security;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    // Handler personalizado para respuestas 403 Forbidden
    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":true,\"message\":\"Acceso denegado: no tienes permisos para esta ruta.\"}");
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso sin autenticación a estas rutas
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/Usuarios").permitAll() // Permitir POST público para crear usuario

                // Rutas solo para ADMINISTRADOR
                .requestMatchers("/admin", "/adcrear", "/Inventario", "/Solielemento", "/Soliespacio").hasRole("ADMINISTRADOR")

                // Rutas solo para TECNICO (solo estas rutas, ninguna más)
                .requestMatchers("/Prestamos-Tecnico", "/Tickets-Tecnico", "/TicketsActivos", "/PrestamosActivos", "/HistorialTec").hasRole("TECNICO")

                // Rutas específicas por rol (más restrictivas primero)
                .requestMatchers("/api/admin/**").hasAnyRole("ADMINISTRADOR", "ADMIN")
                // Eliminar acceso general a /api/tecnico/** y /api/usuario/** para TECNICO
                .requestMatchers("/api/tecnico/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/api/usuario/**").hasAnyRole("USUARIO", "INSTRUCTOR", "ADMINISTRADOR")
                // Cualquier otra petición requiere autenticación y no será accesible para TECNICO
                .anyRequest().denyAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(new CustomAccessDeniedHandler())
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}


// --- Configuración global de CORS para desarrollo React ---
@Configuration
class CorsGlobalConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:5173", "http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
