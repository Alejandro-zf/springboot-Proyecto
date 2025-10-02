package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.TrasabilidadServices;
import com.proyecto.trabajo.dto.TrasabilidadDtos;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/trasabilidad")
public class TrasabilidadController {

    private final TrasabilidadServices trasabilidadServices;

    public TrasabilidadController(TrasabilidadServices trasabilidadServices) {
        this.trasabilidadServices = trasabilidadServices;
    }


    //Obtener por el ID
    @GetMapping("/{id}")
    public ResponseEntity<TrasabilidadDtos> obtenerPorId(@RequestParam Long id) {
        TrasabilidadDtos trasabilidad = trasabilidadServices.buscarPorId(id);
        return ResponseEntity.ok(trasabilidad);
    }
    

    //Listar trasabilidad 
    @GetMapping
    public ResponseEntity<List<TrasabilidadDtos>> listarTodos() {
        List<TrasabilidadDtos> trasabilidades = trasabilidadServices.listarTodos();
        return ResponseEntity.ok(trasabilidades);
    }

    //Eliminar trasabilidad
    @DeleteMapping("/id")
    public ResponseEntity<Void> eliminar(@RequestParam Long id) {
        trasabilidadServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
}
