package com.proyecto.trabajo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.trabajo.Services.ElementosServices;
import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementoUpdateDtos;
import com.proyecto.trabajo.dto.ElementosCreateDto;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/elementos")
public class ElementoController {

    private final ElementosServices elementosServices;

    public ElementoController(ElementosServices elementosServices){
        this.elementosServices = elementosServices;
    }

    //Crear elemento - Acceso: Admin(Instructor y tecnico NO puede crear)
    @PostMapping
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> crear(@Valid @RequestBody ElementosCreateDto dto) {
        try{
            ElementoDto creado = elementosServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Elemento creado con exito", "data", creado));
        }catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errores1", ex.getMessage()));
        } catch (Exception ex) {
            String detalle = ex.getMessage();
            if (detalle != null && detalle.contains("Const_elemento_prestamo")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "success",
                    "Error al crear elemento",
                    "mensaje", "El elemento ya ha sido asignado a otro prestamo"));
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("errores2", "Error3 al crear el elemento", "detalle", ex.getMessage()));
        }
    }
    
    //Obtener por ID - Acceso: Admin, Tecnico, Instructor
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<ElementoDto> obtenerPorId(@PathVariable Long id){
        ElementoDto elemento = elementosServices.buscarPorId(id);
        return ResponseEntity.ok(elemento); 
    }

    //Listar todos - Acceso: Admin, Tecnico, Instructor
    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<ElementoDto>> listarTodos(){
        List<ElementoDto> elementos = elementosServices.listarTodos();
        return ResponseEntity.ok(elementos);
    }

    //Eliminar elementos - Acceso: Solo Admin (Tecnico e Instructor NO pueden)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> eliminar (@PathVariable Long id){
        try {
            elementosServices.eliminar(id);
            return ResponseEntity.ok(Map.of("mensaje", "Elemento eliminado exitosamente"));
        } catch (IllegalStateException ex) {
            // Error de validación de negocio (préstamos o tickets asociados)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            // Error inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al eliminar el elemento", "detalle", ex.getMessage()));
        }
    }

    // Actualizar elemento (por ejemplo cambiar estado) - Acceso: Solo Administrador
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ElementoUpdateDtos dto) {
        try {
            var actualizado = elementosServices.actualizarElemento(id, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Elemento actualizado", "data", actualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
        }
    }

    // Carga masiva desde Excel (.xlsx) - Solo Admin
    @PostMapping("/bulk-upload")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> subirMasivo(@RequestParam("file") MultipartFile file) {
        try {
            var resultado = elementosServices.guardarMasivo(file);
            return ResponseEntity.ok(Map.of("mensaje", "Carga finalizada", "resultado", resultado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
        }
    }

    // Descargar plantilla XLSX
    @GetMapping("/template")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<byte[]> descargarPlantilla() {
        try {
            byte[] archivo = elementosServices.generarPlantilla();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=plantilla_elementos.xlsx");
            return new ResponseEntity<>(archivo, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
