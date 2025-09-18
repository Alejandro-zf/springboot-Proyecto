package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.ElementosServices;
import com.proyecto.trabajo.dto.ElementoDto;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
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
        ElementoDto elemento = elementosServices.guardar(dto);
        
    }
        
    }
    
