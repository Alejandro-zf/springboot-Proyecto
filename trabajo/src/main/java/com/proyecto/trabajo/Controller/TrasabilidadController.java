package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.TrasabilidadServices;
import com.proyecto.trabajo.dto.TrasabilidadDtos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/trasabilidad")
public class TrasabilidadController {

    private final TrasabilidadServices trasabilidadServices;

    public TrasabilidadController(TrasabilidadServices trasabilidadServices) {
        this.trasabilidadServices = trasabilidadServices;
    }


    //Obtener por el ID
    @GetMapping("/{id}")
    public ResponseEntity<TrasabilidadDtos> obtenerPorId(@PathVariable Long id) {
        TrasabilidadDtos trasabilidad = trasabilidadServices.buscarPorId(id);
        return ResponseEntity.ok(trasabilidad);
    }

    // Obtener historial por id de ticket
    @GetMapping("/ticket/{id}")
    public ResponseEntity<List<TrasabilidadDtos>> obtenerPorTicketId(@PathVariable Long id) {
        List<TrasabilidadDtos> historial = trasabilidadServices.buscarPorTicketId(id);
        return ResponseEntity.ok(historial);
    }
    

    //Listar trasabilidad 
    @GetMapping
    public ResponseEntity<List<TrasabilidadDtos>> listarTodos() {
        List<TrasabilidadDtos> trasabilidades = trasabilidadServices.listarTodos();
        return ResponseEntity.ok(trasabilidades);
    }

    //Eliminar trasabilidad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        trasabilidadServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody com.proyecto.trabajo.dto.TrasabilidadUpdateDtos dto) {
        try {
            var actualizado = trasabilidadServices.actualizarTrasabilidad(id, dto);
            return ResponseEntity.ok(java.util.Map.of("mensaje", "Trasabilidad actualizada", "data", actualizado));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(java.util.Map.of("error", ex.getMessage()));
        }
    }
    
}
