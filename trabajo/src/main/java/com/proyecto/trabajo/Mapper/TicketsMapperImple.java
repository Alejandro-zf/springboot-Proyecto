package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Tickets;

@Component
public class TicketsMapperImple implements TicketsMapper {

    @Override
    public Tickets toEntity(TicketsDtos dto) {
        if (dto == null) {
            return null;
        }
        Tickets entity = new Tickets();
        entity.setId(dto.getId_tickets());
        entity.setFecha_ini(dto.getFecha_in());
        entity.setFecha_finn(dto.getFecha_fin());
        entity.setAmbiente(dto.getAmbient());
    
        return entity;
    }

    @Override
    public TicketsDtos toDTO(Tickets entity) {
        if (entity == null) {
            return null;
        }
        TicketsDtos dto = new TicketsDtos();
        dto.setId_tickets(entity.getId());
        dto.setFecha_in(entity.getFecha_ini());
        dto.setFecha_fin(entity.getFecha_finn());
        dto.setAmbient(entity.getAmbiente());
        return dto;
    }
}
