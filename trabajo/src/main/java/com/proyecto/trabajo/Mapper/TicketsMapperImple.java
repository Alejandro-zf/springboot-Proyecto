package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Estado_ticket;

@Component
public class TicketsMapperImple implements TicketsMapper {

    @Override
    public Tickets toTickets(TicketsDtos ticketsDtos) {
        if (ticketsDtos == null) {
            return null;
        }
        Tickets tickets = new Tickets();
        tickets.setId(ticketsDtos.getId_tickets());
        tickets.setFecha_ini(ticketsDtos.getFecha_in());
        tickets.setFecha_finn(ticketsDtos.getFecha_fin());
        tickets.setAmbiente(ticketsDtos.getAmbient());
        tickets.setUsuario(ticketsDtos.getUsuario());
        tickets.setEstado_ticket(ticketsDtos.getEstado_ticket());

        return tickets;
    }

    @Override
    public TicketsDtos toTicketsDto(Tickets tickets) {
        if (tickets == null) {
            return null;
        }
        TicketsDtos ticketsDto = new TicketsDtos();
        ticketsDto.setId_tickets(tickets.getId());
        ticketsDto.setFecha_in(tickets.getFecha_ini());
        ticketsDto.setFecha_fin(tickets.getFecha_finn());
        ticketsDto.setAmbient(tickets.getAmbiente());
        ticketsDto.setUsuario(tickets.getUsuario());
        ticketsDto.setEstado_ticket(tickets.getEstado_ticket());
        
        return ticketsDto;
    }

    @Override
    public List<TicketsDtos> toTicketsDtoList(List<Tickets> tickets) {
        if (tickets == null) {
            return List.of();
        }
        List<TicketsDtos> ticketsDtos = new ArrayList<TicketsDtos>(tickets.size());

        for (Tickets ticket : tickets) {
            ticketsDtos.add(toTicketsDto(ticket));
        }
        return ticketsDtos;
    }
}
