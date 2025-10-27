package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.dto.TicketsCreateDto;

public interface TicketsMapper {
    Tickets toTickets(TicketsDtos ticketsDtos);
    TicketsDtos toTicketsDto(Tickets tickets);
    Tickets toTicketsFromCreateDto(TicketsCreateDto createDto);
    default void mapEstadoTicket(Tickets ticket, TicketsDtos dto) {
        if (ticket.getEstado_ticket() != null) {
            
            Byte idEstado = ticket.getEstado_ticket().getId_estado();
            dto.setId_est_tick(idEstado != null ? idEstado.longValue() : null);
            
            switch (ticket.getEstado_ticket().getId_estado()) {
                case 1:
                    dto.setTip_est_ticket("Aprobado");
                    break;
                case 2:
                    dto.setTip_est_ticket("Pendiente");
                    break;
                case 3:
                    dto.setTip_est_ticket("Terminado");
                    break;
                default:
                    dto.setTip_est_ticket("Desconocido");
            }
        }
    }
}