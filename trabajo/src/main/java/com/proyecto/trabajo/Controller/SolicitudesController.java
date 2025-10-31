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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudesController {
    // Actualizar estado de solicitud - Acceso: Solo Tecnico (Admin e Instructor NO pueden)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Tecnico')")
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
    //Crear solicitud - Acceso: Tecnico, Instructor (Admin NO puede crear)
    @PostMapping
    @PreAuthorize("hasAnyRole('Tecnico', 'Instructor')")

    public ResponseEntity<?> crear (@Valid @RequestBody SolicitudeCreateDto dto){
        try{
            SolicitudesDto creado = solicitudesServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("mensaje","Solicitud creada exitosamente","data",creado));
        }catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("errores1", ex.getMessage()));
        }
    }

    //Obtener solicitudes por ID - Acceso: Admin, TECNICO, INSTRUCTOR
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")
    public ResponseEntity<SolicitudesDto> obtenerporId(@PathVariable Long id){
        SolicitudesDto solicitudes = solicitudesServices.buscarPorId(id);
        return ResponseEntity.ok(solicitudes);
    }
    
    //Listar todas las solicitudes - Acceso: Admin, TECNICO, INSTRUCTOR
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")
    public ResponseEntity<List<SolicitudesDto>> listarTodos(){
        List<SolicitudesDto> solicitudes = solicitudesServices.listarTodos();
        return ResponseEntity.ok(solicitudes);
    }

    //Eliminar solicitud - Acceso: Solo Tecnico (Admin e Instructor NO pueden)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Tecnico')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        solicitudesServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    // Expirar solicitudes vencidas manualmente - Acceso: Admin, TECNICO, INSTRUCTOR
    @PostMapping("/expirar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")
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