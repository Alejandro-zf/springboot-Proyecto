package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.PrestamosServices;
import com.proyecto.trabajo.dto.PrestamosDto;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/pretsamos")

public class PrestamosController {

    private final PrestamosServices prestamosServices;

    public PrestamosController(PrestamosServices prestamosServices) {
        this.prestamosServices = prestamosServices;
    }

    //Crear prestamo  
    @PostMapping
    public ResponseEntity<PrestamosDto> crear(@Valid @RequestBody PrestamosDto dto) {
        PrestamosDto nuevoPrestamo = prestamosServices.guardar(dto);
        return ResponseEntity.ok(nuevoPrestamo);
    }

    //Obtener prestamos por el id
    @GetMapping("/{id}")
    public ResponseEntity<PrestamosDto> buscarPorId(@PathVariable Long id) {
        PrestamosDto prestamo = prestamosServices.buscarPorId(id);
        return ResponseEntity.ok(prestamo);
    }
    
    //Listar los prestamos 
    @GetMapping
    public ResponseEntity<List<PrestamosDto>> listarTodos() {
    List<PrestamosDto> prestamos = prestamosServices.listarTodos();
    return ResponseEntity.ok(prestamos);
    }

    //Eliminar prestamos por el id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        prestamosServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
