package com.proyecto.trabajo.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            Map.of(
                "error", ex.getMessage(),
                "codigo", 404,
                "timestamp", LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarArgumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            Map.of(
                "error", ex.getMessage(),
                "codigo", 400,
                "timestamp", LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleConflict(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        
        if (ex.getMessage() != null && ex.getMessage().contains("desactivado")) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex.getMessage() != null && (ex.getMessage().contains("Credenciales") || ex.getMessage().contains("incorrectas"))) {
            status = HttpStatus.UNAUTHORIZED;
        }
        
        return ResponseEntity.status(status).body(
            Map.of(
                "error", ex.getMessage() != null ? ex.getMessage() : "Error en la autenticación",
                "codigo", status.value(),
                "timestamp", LocalDateTime.now()));
    }

}
