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
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<?> actualizarSolicitud(@PathVariable Long id, @RequestBody SolicitudesUpdateDtos dto) {
        try {
            SolicitudesDto actualizado = solicitudesServices.actualizarSolicitud(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud actualizada correctamente", "data", actualizado));
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = ex.getMessage() != null ? ex.getMessage() : "Error inesperado al actualizar solicitud";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", msg));
        }
    }
    // Actualizar estado de solicitud - Acceso: Administrador, Tecnico e Instructor
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody SolicitudesUpdateDtos dto) {
        try {
            SolicitudesDto actualizado = solicitudesServices.actualizarSolicitud(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud actualizada", "data", actualizado));
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = ex.getMessage() != null ? ex.getMessage() : "Error inesperado al actualizar estado";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", msg));
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
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = ex.getMessage() != null ? ex.getMessage() : "Error inesperado al crear solicitud";
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", msg));
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
    // Cancelar solicitud - Solo Instructor dueño puede cancelar
    @PutMapping("/cancelar/{id}")
    @PreAuthorize("hasAnyRole('Instructor', 'Administrador', 'Tecnico')")
    public ResponseEntity<?> cancelarSolicitud(@PathVariable Long id, @RequestBody SolicitudesUpdateDtos dto, Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "No autenticado"));
            }
            String username = authentication.getName();
            List<String> roles = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();
            boolean isAdminOrTecnico = roles.contains("ROLE_Administrador") || roles.contains("ROLE_Tecnico");
            SolicitudesDto solicitud = solicitudesServices.buscarPorId(id);
            System.out.println("[CANCELAR] Usuario: " + username);
            System.out.println("[CANCELAR] Roles: " + roles);
            System.out.println("[CANCELAR] Correo solicitud: " + (solicitud != null ? solicitud.getCorreo() : "null"));
            System.out.println("[CANCELAR] Id usuario solicitud: " + (solicitud != null ? solicitud.getId_usu() : "null"));
            // Permitir si es el dueño (por correo o id_usu) o si es admin/tecnico
            boolean isOwner = false;
            if (solicitud != null) {
                // Si el username es igual al correo o al id_usu (como string)
                isOwner = username.equals(solicitud.getCorreo()) || username.equals(String.valueOf(solicitud.getId_usu()));
            }
            if (solicitud == null || (!isOwner && !isAdminOrTecnico)) {
                System.out.println("[CANCELAR] Permiso denegado: username=" + username + ", correoSolicitud=" + (solicitud != null ? solicitud.getCorreo() : "null") + ", id_usu=" + (solicitud != null ? solicitud.getId_usu() : "null"));
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "No tienes permiso para cancelar esta solicitud"));
            }
            // Solo permitir cancelar (por ejemplo, id_est_soli = 4)
            if (dto.getId_est_soli() == null || dto.getId_est_soli() != 4) {
                System.out.println("[CANCELAR] id_est_soli inválido: " + dto.getId_est_soli());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Solo se permite cancelar la solicitud (id_est_soli=4)"));
            }
            SolicitudesDto actualizado = solicitudesServices.actualizarSolicitud(id, dto);
            System.out.println("[CANCELAR] Solicitud cancelada correctamente");
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud cancelada correctamente", "data", actualizado));
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg = ex.getMessage() != null ? ex.getMessage() : "Error inesperado al cancelar solicitud";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", msg));
        }
    }
}