package com.proyecto.trabajo.Controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.CategoriaServices;
import com.proyecto.trabajo.dto.CategoriaCreateDtos;
import com.proyecto.trabajo.dto.CategoriaDtos;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    private final CategoriaServices categoriaServices;

    public CategoriaController(CategoriaServices categoriaServices) {
        this.categoriaServices = categoriaServices;
    }

    //crear Categoria - Acceso: Admin, Tecnico, Instructor
    @PostMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<?> crear (@Valid @RequestBody CategoriaCreateDtos dto){
        try{
            CategoriaDtos creado = categoriaServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("mensaje","Categoria creada exitosamente","data",creado));
        }catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("errores1", ex.getMessage()));
        }
    }

    //Obtener categoria por ID - Acceso: Admin, Tecnico, Instructor
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<CategoriaDtos> obtenerporId(@PathVariable byte id){
        CategoriaDtos categoria = categoriaServices.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }
    
    //Listar todas las categorias - Acceso: Admin, Tecnico, Instructor
    @GetMapping
    @PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
    public ResponseEntity<List<CategoriaDtos>> listarTodos(){
        List<CategoriaDtos> categorias = categoriaServices.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    //Eliminar categoria - Acceso: Solo Admin (Tecnico e Instructor NO pueden)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> eliminar(@PathVariable byte id){
        categoriaServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
