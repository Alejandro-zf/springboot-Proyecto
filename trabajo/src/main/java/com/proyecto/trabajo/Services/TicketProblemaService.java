package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.dto.TicketProblemaDto;
import com.proyecto.trabajo.Mapper.TicketProblemaMapper;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.TicketProblema;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.repository.ProblemasRepository;
import com.proyecto.trabajo.repository.TicketProblemaRepository;
import com.proyecto.trabajo.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketProblemaService {
    @Autowired
    private TicketProblemaRepository ticketProblemaRepository;
    @Autowired
    private TicketsRepository ticketsRepository;
    @Autowired
    private ProblemasRepository problemasRepository;

    public TicketProblemaDto save(TicketProblemaDto dto) {
        Tickets ticket = ticketsRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        Problemas problema = problemasRepository.findById(dto.getProblemaId())
                .orElseThrow(() -> new RuntimeException("Problema no encontrado"));
        TicketProblema entity = TicketProblemaMapper.toEntity(dto, ticket, problema);
        TicketProblema saved = ticketProblemaRepository.save(entity);
        return TicketProblemaMapper.toDto(saved);
    }

    public List<TicketProblemaDto> findByTicketId(Long ticketId) {
        return ticketProblemaRepository.findByTicketId(ticketId)
                .stream()
                .map(TicketProblemaMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TicketProblemaDto> findByProblemaId(Long problemaId) {
        return ticketProblemaRepository.findByProblemaId(problemaId)
                .stream()
                .map(TicketProblemaMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TicketProblemaDto> findById(Long id) {
        return ticketProblemaRepository.findById(id).map(TicketProblemaMapper::toDto);
    }

    public void delete(Long id) {
        ticketProblemaRepository.deleteById(id);
    }
}
