package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.SolicitudesServices;
import com.proyecto.trabajo.dto.SolicitudesDto;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudesController {

    private final SolicitudesServices solicitudesServices;

    public SolicitudesController(SolicitudesServices solicitudesServices){
        this.solicitudesServices = solicitudesServices;
    }

    //Crear solicitud
    @PostMapping
    public ResponseEntity<SolicitudesDto> crear(@Valid @RequestBody SolicitudesDto dto){
        SolicitudesDto creado = solicitudesServices.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }



}
