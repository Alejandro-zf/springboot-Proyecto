package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;

public interface TicketsMapper {
    Tickets toEntity(TicketsDtos dto);
    TicketsDtos toDTO(Tickets entity);
}
