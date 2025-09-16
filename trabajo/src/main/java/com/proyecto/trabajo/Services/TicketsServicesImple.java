package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.TicketsMapper;
import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.repository.TicketsRepository;

@Service
public class TicketsServicesImple implements TicketsServices {
    
    private final TicketsRepository ticketsRepo;
    private final TicketsMapper ticketsMapper;

    public TicketsServicesImple(TicketsRepository ticketsRepo, TicketsMapper ticketsMapper) {
        this.ticketsRepo = ticketsRepo;
        this.ticketsMapper = ticketsMapper;
    }

    @Override
    public TicketsDtos guardar(TicketsDtos dto) {
        Tickets tickets = ticketsMapper.toEntity(dto);
        return ticketsMapper.toDTO(ticketsRepo.save(tickets));
    }

    @Override
    public TicketsDtos buscarPorId(Long id) {
        Tickets tickets = ticketsRepo.findById(id).orElse(null);
        return ticketsMapper.toDTO(tickets);
    }

    @Override
    public List<TicketsDtos> listarTodos() {
        List<Tickets> tickets = ticketsRepo.findAll();
        return ticketsMapper.toTicketsDtoList(tickets);
    }

    @Override
    public void eliminar(Long id) {
        ticketsRepo.deleteById(id);
    }
}
