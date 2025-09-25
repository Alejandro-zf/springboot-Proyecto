package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.SolicitudesServices;
import com.proyecto.trabajo.dto.SolicitudesDto;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



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

    //Obtener solicitudes por ID
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudesDto> obtenerporId(@PathVariable Long id){
        SolicitudesDto solicitudes = solicitudesServices.buscarPorId(id);
        return ResponseEntity.ok(solicitudes);
    }
    
    //Listar todas las solicitudes
    @GetMapping 
    public ResponseEntity<List<SolicitudesDto>> listarTodos(){
        List<SolicitudesDto> solicitudes = solicitudesServices.listarTodos();
        return ResponseEntity.ok(solicitudes);
    }

    //Eliminar solicitud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        solicitudesServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
    
