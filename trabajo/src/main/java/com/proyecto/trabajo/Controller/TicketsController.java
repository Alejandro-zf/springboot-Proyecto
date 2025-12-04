package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.TicketsServices;
import com.proyecto.trabajo.Services.FileStorageService;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsUpdateDtos;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.access.AccessDeniedException;
//
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.ExceptionHandler;




@RestController
@RequestMapping("/api/tickets")
public class TicketsController {

    private final TicketsServices ticketsServices;
    private final FileStorageService fileStorageService;

    public TicketsController(TicketsServices ticketsServices, FileStorageService fileStorageService) {
        this.ticketsServices = ticketsServices;
        this.fileStorageService = fileStorageService;
    }

    //Subir imágenes y obtener URLs - Para tickets
    @PostMapping("/upload-images")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<?> uploadImages(@RequestBody Map<String, List<String>> request) {
        try {
            List<String> base64Images = request.get("images");
            if (base64Images == null || base64Images.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "No se proporcionaron imágenes"));
            }

            List<String> imageUrls = new ArrayList<>();
            for (String base64Image : base64Images) {
                // Guardar en la carpeta "tickets"
                String imageUrl = fileStorageService.saveBase64Image(base64Image, "tickets");
                imageUrls.add(imageUrl);
            }

            return ResponseEntity.ok(Map.of("urls", imageUrls));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al subir las imágenes", "detalle", ex.getMessage()));
        }
    }

    //Crear ticket - Acceso: Administrador, Tecnico, Instructor
    @PostMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<?> crear(@Valid  @RequestBody TicketsCreateDto dto){
        try{
            TicketsDtos creado = ticketsServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje","Ticket creado exitosamente","data",creado));
        }catch (IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("errores1", ex.getMessage()));
        }catch(Exception ex){
            String detalle = ex.getMessage();
            if (detalle != null && detalle.contains("Const_tickets_elemento")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "succes",
                    "Error al crear el ticket",
                    "Mensaje", "El ticket ya esta asignado a otro elemento"));
            }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("errores2","Error3 al crear el ticket", "detalle", ex.getMessage()));
        }
    }

    //Obtener ticket por id - Acceso: Admin, Tecnico, Instructor
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<TicketsDtos> obtener(@PathVariable Long id) {
        TicketsDtos tickets = ticketsServices.buscarPorId(id);
        return ResponseEntity.ok(tickets);
    }

    //Listar tickets - Acceso: Admin, Tecnico, Instructor
    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<TicketsDtos>> listarTodos() {
        List<TicketsDtos> tickets = ticketsServices.listarTodos();
        return ResponseEntity.ok(tickets);
    }

    //Listar solo tickets activos - Acceso: Administrador, Técnico, Instructor
    @GetMapping("/activos")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<TicketsDtos>> listarActivos() {
        List<TicketsDtos> tickets = ticketsServices.listarActivos();
        return ResponseEntity.ok(tickets);
    }

    // Listar solo tickets pendientes - Acceso: Administrador, Técnico, Instructor
    @GetMapping("/pendientes")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<TicketsDtos>> listarPendientes() {
        List<TicketsDtos> tickets = ticketsServices.listarPendientes();
        return ResponseEntity.ok(tickets);
    }

    // Listar tickets terminados e inactivos - Acceso: Administrador, Técnico, Instructor
    @GetMapping("/finalizados")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<TicketsDtos>> listarFinalizados() {
        List<TicketsDtos> tickets = ticketsServices.listarFinalizados();
        return ResponseEntity.ok(tickets);
    }

    //Eliminar tickets - Acceso: Administrador y Tecnico
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico')")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        ticketsServices.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Se eliminó exitosamente"));
    }

    //Actualizar ticket - Acceso: solo Técnico
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico')")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody TicketsUpdateDtos dto) {
        try {
            TicketsDtos actualizado = ticketsServices.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Ticket actualizado exitosamente", "data", actualizado));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al actualizar el ticket", "detalle", ex.getMessage()));
        }
    }

    // Actualizar solo el estado del ticket (usado por el frontend técnico)
    @PutMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico')")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            // Buscar el estado por nombre (ejemplo: 'ACTIVO')
            String nuevoEstadoNombre = (String) body.get("estado");
            String observacion = (String) body.getOrDefault("observacion", "");
            if (nuevoEstadoNombre == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "El campo 'estado' es obligatorio"));
            }
            // Buscar el Estado_ticket correspondiente
            // (asume que los nombres en la tabla son 'ACTIVO', 'PENDIENTE', etc.)
            com.proyecto.trabajo.models.Estado_ticket estadoTicket = ticketsServices
                .getEstadoTicketByNombre(nuevoEstadoNombre);
            if (estadoTicket == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Estado no válido: " + nuevoEstadoNombre));
            }
            com.proyecto.trabajo.dto.TicketsUpdateDtos dto = new com.proyecto.trabajo.dto.TicketsUpdateDtos();
            dto.setId_est_tick((long) estadoTicket.getIdEstado());
            // Puedes agregar aquí la observación si tu modelo lo soporta
            // dto.setObservaciones(observacion);
            com.proyecto.trabajo.dto.TicketsDtos actualizado = ticketsServices.actualizar(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Estado actualizado correctamente", "data", actualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al actualizar el estado", "detalle", ex.getMessage()));
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        String rolUsuario = "desconocido";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities() != null) {
            rolUsuario = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .reduce((a, b) -> a + ", " + b)
                .orElse("desconocido");
        }
        String mensaje = "Acceso restringido. Tu rol: " + rolUsuario;
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Map.of(
                "error", true,
                "message", mensaje
            ));
    }
}
