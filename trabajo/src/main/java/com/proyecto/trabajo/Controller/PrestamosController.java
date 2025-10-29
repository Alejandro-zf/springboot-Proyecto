package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.PrestamosServices;
import com.proyecto.trabajo.dto.PrestamosCreateDto;
import com.proyecto.trabajo.dto.PrestamosDto;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/prestamos")

public class PrestamosController {

    private final PrestamosServices prestamosServices;

    public PrestamosController(PrestamosServices prestamosServices) {
        this.prestamosServices = prestamosServices;
    }

    //Crear prestamo - Acceso: Admin, Tecnico, Instructor
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")
    public ResponseEntity<?> crear(@Valid @RequestBody PrestamosCreateDto dto) {
        try{
            PrestamosDto creado = prestamosServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("Mensaje","Prestamo creado exitosamente", "data", creado));
        }catch (IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("errores1", ex.getMessage()));
        }catch (Exception ex){
            String detalle = ex.getMessage();
            if (detalle != null && detalle.contains("Const_prestamos_elementos")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "succes",
                    "Error al crear el prestamo",
                    "mensaje", "El elemento ya esta asignado a otro prestamo"));
            } 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("errores2", "Error3 al crear el prestamo", "detalle", ex.getMessage()));
        }
}
    //Obtener prestamos por el id - Acceso: Admin, Tecnico, Instructor
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")
    public ResponseEntity<PrestamosDto> buscarPorId(@PathVariable Long id) {
        PrestamosDto prestamo = prestamosServices.buscarPorId(id);
        return ResponseEntity.ok(prestamo);
    }
    

    // Listar todos los préstamos - Acceso: Admin, Tecnico, Instructor
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")
    public ResponseEntity<List<PrestamosDto>> listarTodos() {
        List<PrestamosDto> prestamos = prestamosServices.listarTodos();
        return ResponseEntity.ok(prestamos);
    }

    // Listar préstamos activos (estado=1) - Acceso: Admin, Tecnico, Instructor
    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")
    public ResponseEntity<List<PrestamosDto>> listarActivos() {
        List<PrestamosDto> activos = prestamosServices.listarActivos();
        return ResponseEntity.ok(activos);
    }

    //Eliminar prestamos por el id - Acceso: Solo Admin (Tecnico e Instructor NO pueden)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        prestamosServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
