package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
    public TicketsDtos guardar(TicketsDtos dto) {
        Tickets entity = ticketsMapper.toEntity(dto);
        Tickets guardado = ticketsRepository.save(entity);
        return ticketsMapper.toDTO(guardado);
    }

    @Override
    public TicketsDtos buscarPorId(Long id) {
        return ticketsRepository.findById(id)
                .map(ticketsMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
    }

    @Override
    public List<TicketsDtos> listarTodos() {
        return ticketsRepository.findAll()
                .stream()
                .map(ticketsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        ticketsRepository.deleteById(id);
    }
}