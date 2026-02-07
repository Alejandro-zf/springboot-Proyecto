package com.proyecto.trabajo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.ProblemasServices;
import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.dto.ProblemasUpdateDtos;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/problemas")
public class ProblemasController {

    private final ProblemasServices problemasServices;

    public ProblemasController(ProblemasServices problemasServices) {
        this.problemasServices = problemasServices;
    }

    // Crear problema - Acceso: Admin y Tecnico
    @PostMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico')")
    public ResponseEntity<?> crear(@Valid @RequestBody ProblemasCreateDtos dto) {
        try {
            ProblemasDtos creado = problemasServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Problema creado exitosamente", "data", creado));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al crear el problema", "detalle", ex.getMessage()));
        }
    }

    // Obtener problema por ID - Acceso: Admin, Tecnico, Instructor
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<?> obtenerPorId(@PathVariable Byte id) {
        try {
            ProblemasDtos problema = problemasServices.buscarPorId(id);
            return ResponseEntity.ok(problema);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Problema no encontrado"));
        }
    }

    // Listar todos los problemas - Acceso: Admin, Tecnico, Instructor
    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<ProblemasDtos>> listarTodos() {
        List<ProblemasDtos> problemas = problemasServices.listarTodos();
        return ResponseEntity.ok(problemas);
    }

    // Actualizar problema - Acceso: Admin y Tecnico
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico')")
    public ResponseEntity<?> actualizar(@PathVariable Byte id, @Valid @RequestBody ProblemasUpdateDtos dto) {
        try {
            ProblemasDtos actualizado = problemasServices.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Problema actualizado correctamente", "data", actualizado));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
        }
    }

    // Eliminar problema - Acceso: Solo Admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> eliminar(@PathVariable Byte id) {
        try {
            problemasServices.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
        }
    }
}