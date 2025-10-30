package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.ElementosServices;
import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementosCreateDto;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/elementos")
public class ElementoController {

    private final ElementosServices elementosServices;

    public ElementoController(ElementosServices elementosServices){
        this.elementosServices = elementosServices;
    }

    //Crear elemento - Acceso: Admin, Tecnico, Instructor
    @PostMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
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
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        elementosServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
