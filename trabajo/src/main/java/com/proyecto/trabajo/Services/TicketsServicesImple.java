package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.TicketsMapper;
import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.repository.TicketsRepository;

@Service
public class TicketsServicesImple implements TicketsServices {
    
    @Autowired
    private TicketsRepository ticketsRepo;

    @Autowired
    private TicketsMapper ticketsMapper;

    @Override
    public TicketsDtos getTicket(Long id_tickets) {
        Tickets tickets = ticketsRepo.findById(id_tickets).get();
        return ticketsMapper.toTicketsDto(tickets);
    }

    @Override
    public TicketsDtos saveTicket(TicketsDtos ticketsDtos) {
        Tickets tickets = ticketsMapper.toTickets(ticketsDtos);
        return ticketsMapper.toTicketsDto(ticketsRepo.save(tickets));
    }

    @Override
    public List<TicketsDtos> getAllTickets() {
        List<Tickets> tickets = ticketsRepo.findAll();
        return ticketsMapper.toTicketsDtoList(tickets);
    }

    @Override
    public TicketsDtos deleteTickets(Long id_tickets) {
        Tickets tickets = ticketsRepo.findById(id_tickets).get();
        ticketsRepo.delete(tickets);
        return ticketsMapper.toTicketsDto(tickets);
    }
}
