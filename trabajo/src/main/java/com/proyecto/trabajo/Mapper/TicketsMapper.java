package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;

public interface TicketsMapper {
    Tickets toTickets(TicketsDtos ticketsDtos);
    TicketsDtos toTicketsDto(Tickets tickets);
    List<TicketsDtos> toTicketsDtoList(List<Tickets> tickets);
}
