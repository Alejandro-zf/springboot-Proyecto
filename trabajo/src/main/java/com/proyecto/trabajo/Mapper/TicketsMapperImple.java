package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;

@Component
public class TicketsMapperImple {
@Override
public Tickets toTickets(TicketsDtos ticketsDtos){
    if (ticketsDtos == null){
        return null ;
    }
    Tickets tickets = new Tickets();
    tickets.setId(ticketsDtos.getId_tickets());
    tickets.setFecha_ini(ticketsDtos.getFecha_in());
    tickets.setFecha_finn(ticketsDtos.getFecha_fin());
    tickets.setAmbiente(ticketsDtos.getAmbient());

    return tickets;
}
