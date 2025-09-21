package com.proyecto.trabajo.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.proyecto.trabajo.Services.Prestamos_ElementoService;
import com.proyecto.trabajo.dto.Prestamos_ElementoDto;

import jakarta.validation.Valid;

public class Prestamos_ElementoController {
    private final Prestamos_ElementoService service;
    public Prestamos_ElementoController(Prestamos_ElementoService service) {
        this.service = service;

    }

    //Asignar Prestamos a elementos
    @PostMapping
    public ResponseEntity<Prestamos_ElementoDto> asignar(@Valid @RequestBody Prestamos_ElementoDto dto){
        Prestamos_ElementoDto asignado = service.asignar(dto);
        return ResponseEntity.ok(asignado);
    }


    @PostMapping("/Varios")
    public ResponseEntity<List<Prestamos_ElementoDto>> asignarMasivo(
        @RequestBody List<Prestamos_ElementoDto> asignaciones){
            List<Prestamos_ElementoDto> prestaele = service.asignarElementos(asignaciones);
            return ResponseEntity.ok(prestaele);
        }
    
    //Consultar asignaciones por prestamos
    @GetMapping("/prestamos/{id}")
    public ResponseEntity<List<Prestamos_ElementoDto>>listarPorPrestamo(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorPrestamo(id));
    }  

    //Consultar asignaciones por elementos
    @GetMapping("/elementos/{id}")
    public ResponseEntity<List<Prestamos_ElementoDto>>listarPorElemento(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorElemento(id));
    }


    //Eliminar asignaciones especificas
    @DeleteMapping("/{prestamosId}/{elementosId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long prestamosId, @PathVariable Long elementosId){
        service.eliminarAsignacion(prestamosId, elementosId);
        return ResponseEntity.noContent().build();
    }
}