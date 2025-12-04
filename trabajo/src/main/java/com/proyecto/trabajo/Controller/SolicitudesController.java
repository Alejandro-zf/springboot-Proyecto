package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.PutMapping;
import com.proyecto.trabajo.dto.SolicitudesUpdateDtos;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.SolicitudesServices;
import com.proyecto.trabajo.dto.SolicitudeCreateDto;
import com.proyecto.trabajo.dto.SolicitudesDto;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudesController {
    // Actualizar solicitud - Acceso: Administrador y Tecnico
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico')")
    public ResponseEntity<?> actualizarSolicitud(@PathVariable Long id, @RequestBody SolicitudesUpdateDtos dto) {
        try {
            SolicitudesDto actualizado = solicitudesServices.actualizarSolicitud(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud actualizada correctamente", "data", actualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
        }
    }
    // Actualizar estado de solicitud - Acceso: Administrador y Tecnico
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico')")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody SolicitudesUpdateDtos dto) {
        try {
            SolicitudesDto actualizado = solicitudesServices.actualizarSolicitud(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud actualizada", "data", actualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
        }
    }

    private final SolicitudesServices solicitudesServices;

    public SolicitudesController(SolicitudesServices solicitudesServices){
        this.solicitudesServices = solicitudesServices;
    }
    //Crear solicitud - Acceso: Administrador, Tecnico, Instructor
    @PostMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")

    public ResponseEntity<?> crear(@Valid @RequestBody SolicitudeCreateDto dto, Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "No autenticado"));
            }
            String username = authentication.getName();
            SolicitudesDto creado = solicitudesServices.guardar(dto, username);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Solicitud creada exitosamente", "data", creado));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("errores1", ex.getMessage()));
        }
    }

    //Obtener solicitudes por ID - Acceso: Administrador, Tecnico, Instructor
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<SolicitudesDto> obtenerporId(@PathVariable Long id){
        SolicitudesDto solicitudes = solicitudesServices.buscarPorId(id);
        return ResponseEntity.ok(solicitudes);
    }

    //Listar solicitudes pendientes - Acceso: Administrador, Tecnico, Instructor
    @GetMapping("/pendientes")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<SolicitudesDto>> listarPendientes(){
        List<SolicitudesDto> solicitudes = solicitudesServices.listarPendientes();
        return ResponseEntity.ok(solicitudes);
    }

    //Listar todas las solicitudes - Acceso: Administrador, Tecnico, Instructor
    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<SolicitudesDto>> listarTodos(){
        List<SolicitudesDto> solicitudes = solicitudesServices.listarTodos();
        return ResponseEntity.ok(solicitudes);
    }
    // Expirar solicitudes vencidas manualmente - Acceso: Administrador, Tecnico, Instructor
    @PostMapping("/expirar")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<?> expirarVencidas() {
        try {
            solicitudesServices.expirarSolicitudesVencidas();
            return ResponseEntity.ok(Map.of("mensaje", "Expiración ejecutada"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", ex.getMessage()));
            }
        }
    }