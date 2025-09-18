package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.TicketsMapper;
import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.repository.TicketsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TicketsServicesImple implements TicketsServices {

    private final TicketsRepository ticketsRepository;
    private final TicketsMapper ticketsMapper;

    public TicketsServicesImple(TicketsRepository ticketsRepository, TicketsMapper ticketsMapper) {
        this.ticketsRepository = ticketsRepository;
        this.ticketsMapper = ticketsMapper;
    }

    @Override
    @Transactional
    public TicketsDtos guardar(TicketsDtos dto) {
        Tickets tickets = ticketsMapper.toTickets(dto);
        Tickets guardado = ticketsRepository.save(tickets);
        return ticketsMapper.toTicketsDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketsDtos buscarPorId(Long id) {
        Tickets tickets = ticketsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        return ticketsMapper.toTicketsDto(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarTodos() {
        return ticketsRepository.findAll()
                .stream()
                .map(ticketsMapper::toTicketsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Tickets tickets = ticketsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        ticketsRepository.delete(tickets);
    }

    @Override
    @Transactional
    public TicketsDtos actualizarTicket(TicketsDtos dto) {
        Tickets tickets = ticketsRepository.findById(dto.getId_tickets())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        
        tickets.setFecha_ini(dto.getFecha_in());
        tickets.setFecha_finn(dto.getFecha_fin());
        tickets.setAmbiente(dto.getAmbient());
        
        Tickets actualizado = ticketsRepository.save(tickets);
        return ticketsMapper.toTicketsDto(actualizado);
    }
}