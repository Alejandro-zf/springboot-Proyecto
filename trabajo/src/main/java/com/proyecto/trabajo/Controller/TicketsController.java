package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.TicketsServices;
import com.proyecto.trabajo.dto.TicketsDtos;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/tickets")
public class TicketsController {

    private final TicketsServices ticketsServices;

    public TicketsController(TicketsServices ticketsServices) {
        this.ticketsServices = ticketsServices;
    }


    //Crear ticket 
    @PostMapping
    
    public ResponseEntity<TicketsDtos> crear(@Valid @RequestBody TicketsDtos dto) {
        TicketsDtos nuevoTicket = ticketsServices.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTicket);
    }
    

    //Obtener ticket por id
    @GetMapping("/{id}")
    public ResponseEntity<TicketsDtos> obtener(@PathVariable Long id) {
        TicketsDtos tickets = ticketsServices.buscarPorId(id);
        return ResponseEntity.ok(tickets);
    }

    //Listar tickets 
    @GetMapping
    public ResponseEntity<List<TicketsDtos>> listarTodos() {
        List<TicketsDtos> tickets = ticketsServices.listarTodos();
        return ResponseEntity.ok(tickets);
    }

    //Elminar tickets 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ticketsServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
