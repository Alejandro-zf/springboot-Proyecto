package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import java.util.stream.Collectors;

public interface TicketsMapper {
    Tickets toTickets(TicketsDtos ticketsDtos);
    TicketsDtos toTicketsDto(Tickets tickets);
    Tickets toTicketsFromCreateDto(TicketsCreateDto createDto);
    default void mapEstadoTicket(Tickets ticket, TicketsDtos dto) {
        if (ticket.getIdEstTick() != null) {
            Byte idEstado = ticket.getIdEstTick().getIdEstado();
            dto.setId_est_tick(idEstado != null ? idEstado.longValue() : null);
            switch (ticket.getIdEstTick().getIdEstado()) {
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