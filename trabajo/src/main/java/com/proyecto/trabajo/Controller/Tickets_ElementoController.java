package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.Tickets_elementoService;
import com.proyecto.trabajo.dto.Tickets_elementoDto;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/asignacioness")
public class Tickets_ElementoController {
    private final Tickets_elementoService service;
    public Tickets_ElementoController(Tickets_elementoService service){
        this.service = service;
    }

    //Asignar Tickets con elemento
    @PostMapping
    public ResponseEntity<Tickets_elementoDto> asignar (@Valid @RequestBody Tickets_elementoDto dto){
        Tickets_elementoDto asignado = service.asignar(dto);
        return ResponseEntity.ok(asignado);
    }

    @PostMapping("/varios")
    public ResponseEntity<List<Tickets_elementoDto>> asignarMasivo(
        @RequestBody List<Tickets_elementoDto> asignaciones){
            List<Tickets_elementoDto> TicketsPro = service.asignarElementos(asignaciones);
            return ResponseEntity.ok(TicketsPro);
        }

    //Consultar asignaciones por elementos
    @GetMapping("/elemento/{id}")
    public ResponseEntity<List<Tickets_elementoDto>> listarPorElemento(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorElemento(id));
    }
    
    //Consiltar asignaciones por tickets 
    @GetMapping("/tickets/{id}")
    public ResponseEntity<List<Tickets_elementoDto>>listarPorTicket(@PathVariable Long id){
        return ResponseEntity.ok(service.listarPorTicket(id));
    }
    
    //Eliminar asignacion especifica
    @DeleteMapping("/{elementoId}/{ticketsId}")
    public ResponseEntity<Void> eliminar (@PathVariable Long elementoId, @PathVariable long ticketId){
        service.eliminarAsignacion(elementoId, ticketId);
        return ResponseEntity.noContent().build();
    }

}
