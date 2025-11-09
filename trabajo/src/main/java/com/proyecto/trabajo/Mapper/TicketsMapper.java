package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.dto.TicketsCreateDto;

public interface TicketsMapper {
    Tickets toTickets(TicketsDtos ticketsDtos);
    TicketsDtos toTicketsDto(Tickets tickets);
    Tickets toTicketsFromCreateDto(TicketsCreateDto createDto);
    default void mapEstadoTicket(Tickets ticket, TicketsDtos dto) {
        if (ticket.getId_est_tick() != null) {
            Byte idEstado = ticket.getId_est_tick().getId_estado();
            dto.setId_est_tick(idEstado != null ? idEstado.longValue() : null);
            switch (ticket.getId_est_tick().getId_estado()) {
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