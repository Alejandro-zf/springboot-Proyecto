package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.TicketsServices;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.dto.TicketsDtos;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> crear(@Valid  @RequestBody TicketsCreateDto dto){
        try{
            TicketsDtos creado = ticketsServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje","Ticket creado exitosamente","data",creado));
        }catch (IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("errores1", ex.getMessage()));
        }catch(Exception ex){
            String detalle = ex.getMessage();
            if (detalle != null && detalle.contains("Const_tickets_elemento")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "succes",
                    "Error al crear el ticket",
                    "Mensaje", "El ticket ya esta asignado a otro elemento"));
            }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("errores2","Error3 al crear el ticket", "detalle", ex.getMessage()));
        }
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
