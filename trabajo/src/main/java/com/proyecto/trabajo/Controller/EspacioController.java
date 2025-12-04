package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.EspacioServices;
import com.proyecto.trabajo.Services.FileStorageService;
import com.proyecto.trabajo.dto.EspacioCreateDto;
import com.proyecto.trabajo.dto.EspacioDto;
import com.proyecto.trabajo.dto.EspacioUpdateDto;

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

@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    private final EspacioServices espacioServices;
    private final FileStorageService fileStorageService;

    public EspacioController(EspacioServices espacioServices, FileStorageService fileStorageService) {
        this.espacioServices = espacioServices;
        this.fileStorageService = fileStorageService;
    }

    //Subir imágenes y obtener URLs
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
                String imageUrl = fileStorageService.saveBase64Image(base64Image);
                imageUrls.add(imageUrl);
            }

            return ResponseEntity.ok(Map.of("urls", imageUrls));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al subir las imágenes", "detalle", ex.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> crear(@Valid @RequestBody EspacioCreateDto dto) {
        try {
            EspacioDto creado = espacioServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Espacio creado exitosamente", "data", creado));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al crear el espacio", "detalle", ex.getMessage()));
        }
    }

    //Obtener espacio por ID 
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Permitir a cualquier usuario autenticado ver un espacio
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            EspacioDto espacio = espacioServices.buscarPorId(id);
            return ResponseEntity.ok(espacio);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Espacio no encontrado"));
        }
    }

    //Listar espacios 
    @GetMapping
    @PreAuthorize("isAuthenticated()") // Permitir a cualquier usuario autenticado ver los espacios
    public ResponseEntity<List<EspacioDto>> listarTodos() {
        List<EspacioDto> espacios = espacioServices.listarTodos();
        return ResponseEntity.ok(espacios);
    }

    //Actualizar espacio 
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody EspacioUpdateDto dto) {
        try {
            EspacioDto actualizado = espacioServices.actualizarEspacio(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Espacio actualizado correctamente", "data", actualizado));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    //Eliminar espacio 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            espacioServices.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}
