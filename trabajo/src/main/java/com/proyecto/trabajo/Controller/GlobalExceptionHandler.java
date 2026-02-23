package com.proyecto.trabajo.Controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component("globalExceptionHandlerController")
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        // Collect the field names that failed validation
        Set<String> fields = fieldErrors.stream()
            .map(FieldError::getField)
            .collect(Collectors.toSet());

        // If the only problems are missing tipo or descripcion, return short, user-friendly messages
        boolean missingDescr = fields.contains("descr_problem");
        boolean missingTipo = fields.contains("tipo_problema");

        if (missingDescr && !missingTipo) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Tienes que ingresar una descripción"));
        }

        if (missingTipo && !missingDescr) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Tienes que ingresar el tipo"));
        }

        if (missingTipo && missingDescr) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Tienes que ingresar el tipo y la descripción"));
        }

        // Para otros errores (por ejemplo tamaño), devolver un mensaje más corto usando el primer error
        String shortMsg = fieldErrors.stream()
            .map(FieldError::getDefaultMessage)
            .filter(m -> m != null && !m.isBlank())
            .findFirst()
            .orElse("Datos inválidos");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", shortMsg));
    }
}
