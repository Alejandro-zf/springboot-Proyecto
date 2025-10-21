package com.proyecto.trabajo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.SubcategoriaServices;
import com.proyecto.trabajo.dto.SubcategoriaDtos;
import com.proyecto.trabajo.dto.Sub_categoriasCreateDtos;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/subcategoria")
public class SubcategoriaController {

    private final SubcategoriaServices subcategoriaServices;

    public SubcategoriaController(SubcategoriaServices subcategoriaServices) {
        this.subcategoriaServices = subcategoriaServices;
    }

    // Crear Subcategoria
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Sub_categoriasCreateDtos dto) {
        try {
            SubcategoriaDtos creado = subcategoriaServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Subcategoria creada exitosamente", "data", creado));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("errores1", ex.getMessage()));
        }
    }

    // Obtener Subcategoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<SubcategoriaDtos> obtenerPorId(@PathVariable Long id) {
        SubcategoriaDtos subcategoria = subcategoriaServices.buscarPorId(id);
        return ResponseEntity.ok(subcategoria);
    }
    
    // Listar todas las Subcategorias
    @GetMapping
    public ResponseEntity<List<SubcategoriaDtos>> listarTodos() {
        List<SubcategoriaDtos> subcategorias = subcategoriaServices.listarTodos();
        return ResponseEntity.ok(subcategorias);
    }

    // Eliminar Subcategoria
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        subcategoriaServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
