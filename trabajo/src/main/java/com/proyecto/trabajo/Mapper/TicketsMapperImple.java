package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;

@Component
public class TicketsMapperImple implements TicketsMapper {
    
    @Override
    public Tickets toTickets(TicketsDtos ticketsDtos) {
        if (ticketsDtos == null)
            return null;

        Tickets tickets = new Tickets();
        tickets.setId(ticketsDtos.getId_tickets());
        tickets.setFecha_ini(ticketsDtos.getFecha_in());
        tickets.setFecha_finn(ticketsDtos.getFecha_fin());
        tickets.setAmbiente(ticketsDtos.getAmbient());

        return tickets;
    }
    
    @Override
    public TicketsDtos toTicketsDto(Tickets tickets) {
        if (tickets == null)
            return null;

        TicketsDtos ticketsDtos = new TicketsDtos();
        ticketsDtos.setId_tickets(tickets.getId());
        ticketsDtos.setFecha_in(tickets.getFecha_ini());
        ticketsDtos.setFecha_fin(tickets.getFecha_finn());
        ticketsDtos.setAmbient(tickets.getAmbiente());

        return ticketsDtos;
    }
}