package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.TicketsDtos;

public interface TicketsServices {
    public TicketsDtos getTicket(Long id_tickets);

    public TicketsDtos saveTicket(TicketsDtos ticketsDtos);
 
    public List<TicketsDtos> getAllTickets();

    public TicketsDtos deleteTickets(Long id_tickets);
}
