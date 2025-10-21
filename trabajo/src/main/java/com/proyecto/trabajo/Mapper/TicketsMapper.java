package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.dto.TicketsCreateDto;

public interface TicketsMapper {
    Tickets toTickets(TicketsDtos ticketsDtos);
    TicketsDtos toTicketsDto(Tickets tickets);
    Tickets toTicketsFromCreateDto(TicketsCreateDto createDto);
    // Mapear el tipo de estado del ticket
    default void mapEstadoTicket(Tickets ticket, TicketsDtos dto) {
        if (ticket.getEstado_ticket() != null) {
            // Convierte Byte a Long para el DTO
            Byte idEstado = ticket.getEstado_ticket().getId_estado();
            dto.setId_est_tick(idEstado != null ? idEstado.longValue() : null);
            // Usa el nombre real del estado desde la base de datos
            // Mapea según la convención: 1=Aprobado, 2=Pendiente, 3=Terminado
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