package com.proyecto.trabajo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping("/api/subcategorias")
@CrossOrigin(origins = "*")
public class SubcategoriaController {

    private final SubcategoriaServices subcategoriaServices;

    public SubcategoriaController(SubcategoriaServices subcategoriaServices) {
        this.subcategoriaServices = subcategoriaServices;
    }

    @GetMapping
    public ResponseEntity<List<SubcategoriaDtos>> listarSubcategorias() {
        try {
            List<SubcategoriaDtos> subcategorias = subcategoriaServices.listarTodos();
            return ResponseEntity.ok(subcategorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoriaDtos> obtenerSubcategoriaPorId(@PathVariable Long id) {
        try {
            SubcategoriaDtos subcategoria = subcategoriaServices.buscarPorId(id);
            return ResponseEntity.ok(subcategoria);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<SubcategoriaDtos>> listarSubcategoriasPorCategoria(@PathVariable Long idCategoria) {
        try {
            List<SubcategoriaDtos> subcategorias = subcategoriaServices.listarPorCategoria(idCategoria);
            return ResponseEntity.ok(subcategorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<SubcategoriaDtos> crearSubcategoria(@RequestBody Sub_categoriasCreateDtos createDto) {
        try {
            SubcategoriaDtos subcategoriaCreada = subcategoriaServices.guardar(createDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(subcategoriaCreada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubcategoriaDtos> actualizarSubcategoria(
            @PathVariable Long id, 
            @RequestBody Sub_categoriasCreateDtos updateDto) {
        try {
            SubcategoriaDtos subcategoriaActualizada = subcategoriaServices.actualizar(id, updateDto);
            return ResponseEntity.ok(subcategoriaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSubcategoria(@PathVariable Long id) {
        try {
            subcategoriaServices.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
