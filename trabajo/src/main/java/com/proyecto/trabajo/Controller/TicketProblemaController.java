package com.proyecto.trabajo.Controller;

import com.proyecto.trabajo.Services.TicketProblemaService;
import com.proyecto.trabajo.dto.TicketProblemaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticket-problemas")
public class TicketProblemaController {
    @Autowired
    private TicketProblemaService ticketProblemaService;

    @PostMapping("/ticket/{ticketId}")
    public ResponseEntity<TicketProblemaDto> create(@PathVariable Long ticketId, @RequestBody TicketProblemaDto dto) {
        return ResponseEntity.ok(ticketProblemaService.save(ticketId, dto));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<TicketProblemaDto>> getByTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketProblemaService.findByTicketId(ticketId));
    }

    @GetMapping("/problema/{problemaId}")
    public ResponseEntity<List<TicketProblemaDto>> getByProblema(@PathVariable Long problemaId) {
        return ResponseEntity.ok(ticketProblemaService.findByProblemaId(problemaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketProblemaDto> getById(@PathVariable Long id) {
        Optional<TicketProblemaDto> dto = ticketProblemaService.findById(id);
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketProblemaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
