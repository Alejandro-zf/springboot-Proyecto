package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.Elemento_SolicitudesService;
import com.proyecto.trabajo.dto.Elemento_SolicitudesDtos;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/elemento-solicitudes")
public class Elemento_solicitudesController {

    private final Elemento_SolicitudesService Elemento_SolicitudesService;

    public Elemento_solicitudesController(Elemento_SolicitudesService Elemento_SolicitudesService){
        this.Elemento_SolicitudesService = Elemento_SolicitudesService;
    }

    //Asignar elementos a solicitudes
    @PostMapping
    public ResponseEntity<Elemento_SolicitudesDtos> asignar(@Valid @RequestBody Elemento_SolicitudesDtos dto ){
        Elemento_SolicitudesDtos asignado = Elemento_SolicitudesService.asignar(dto);
        return ResponseEntity.ok(asignado);
    }
    @PostMapping("/Varios")
    public ResponseEntity<List<Elemento_SolicitudesDtos>> asignarMasivo(
        @RequestBody List<Elemento_SolicitudesDtos> asignaciones){
            List<Elemento_SolicitudesDtos> elesoli = Elemento_SolicitudesService.asignarElementos(asignaciones);
            return ResponseEntity.ok(elesoli);
        }
    
    //Consultar asignaciones por solicitudes
    @GetMapping("/elementos/{id}")
    public ResponseEntity<List<Elemento_SolicitudesDtos>> listarPorElemento(@PathVariable Long id){
        return ResponseEntity.ok(Elemento_SolicitudesService.listarPorElemento(id));
    }

    //Consultar asignaciones por elementos
    @GetMapping("/solicitudes/{id}")
    public ResponseEntity<List<Elemento_SolicitudesDtos>> listarPorSolicitud(@PathVariable Long id){
        return ResponseEntity.ok(Elemento_SolicitudesService.listarPorSolicitud(id));
    }

    @DeleteMapping("/{solicitudId}/{elementoId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long solicitudId, @PathVariable Long elementoId){
        Elemento_SolicitudesService.eliminarAsignacion(solicitudId, elementoId);
        return ResponseEntity.noContent().build();
    }
    
}
