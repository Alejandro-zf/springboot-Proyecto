package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.proyecto.trabajo.Services.SubcategoriaServices;
import com.proyecto.trabajo.dto.SubcategoriaDtos;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/subcategorias")
public class SubcategoriaController {
    private final SubcategoriaServices subcategoriaServices;

    public SubcategoriaController(SubcategoriaServices subcategoriaServices) {
        this.subcategoriaServices = subcategoriaServices;
    }

    @GetMapping
    public ResponseEntity<List<SubcategoriaDtos>> listarTodos() {
        List<SubcategoriaDtos> subcategorias = subcategoriaServices.listarTodos();
        return ResponseEntity.ok(subcategorias);
    }
}
