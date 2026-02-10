package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.TicketProblemaDto;
import com.proyecto.trabajo.models.TicketProblema;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Problemas;

public class TicketProblemaMapper {
    public static TicketProblemaDto toDto(TicketProblema entity) {
        TicketProblemaDto dto = new TicketProblemaDto();
        dto.setId(entity.getId());
        dto.setTicketId(entity.getTicket() != null ? entity.getTicket().getId() : null);
        dto.setProblemaId(entity.getProblema() != null ? entity.getProblema().getId() : null);
        dto.setDescripcion(entity.getDescripcion());
        dto.setImagenes(entity.getImagenes());
        return dto;
    }

    public static TicketProblema toEntity(TicketProblemaDto dto, Tickets ticket, Problemas problema) {
        TicketProblema entity = new TicketProblema();
        entity.setId(dto.getId());
        entity.setTicket(ticket);
        entity.setProblema(problema);
        entity.setDescripcion(dto.getDescripcion());
        entity.setImagenes(dto.getImagenes());
        return entity;
    }
}
