package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.ElementosServices;
import com.proyecto.trabajo.dto.ElementoDto;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //Crear elemento
    @PostMapping
    public ResponseEntity<ElementoDto> crear (@Valid @RequestBody ElementoDto dto){
        ElementoDto creado = elementosServices.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);   
    }

    //Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<ElementoDto> obtenerPorId(@PathVariable Long id){
        ElementoDto elemento = elementosServices.buscarPorId(id);
        return ResponseEntity.ok(elemento); 
    }

    //Listar todos
    @GetMapping
    public ResponseEntity<List<ElementoDto>> listarTodos(){
        List<ElementoDto> elementos = elementosServices.listarTodos();
        return ResponseEntity.ok(elementos);
    }

    //Eliminar elementos
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        elementosServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}


    
