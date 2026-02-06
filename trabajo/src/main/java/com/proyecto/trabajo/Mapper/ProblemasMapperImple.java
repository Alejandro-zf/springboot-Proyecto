package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.Tickets;

@Component
public class ProblemasMapperImple implements ProblemasMapper {

    @Override
    public Problemas toProblemas(ProblemasDtos dto) {
        if (dto == null) return null;
        Problemas entity = new Problemas();
        
        entity.setId(dto.getId());
        entity.setDesc_problema(dto.getDescr_problem());
        
        Tickets ticket = new Tickets();
        ticket.setId(dto.getId_tick());
        entity.setTicket(ticket);
        
        return entity;
    }

    @Override
    public ProblemasDtos toProblemasDto(Problemas entity) {
        if (entity == null) return null;
        ProblemasDtos dto = new ProblemasDtos();
        
        dto.setId(entity.getId());
        dto.setDescr_problem(entity.getDesc_problema());
        
        if (entity.getTicket() != null) {
            dto.setId_tick(entity.getTicket().getId());
        }
        
        return dto;
    }

    @Override
    public Problemas toProblemasFromCreateDto(ProblemasCreateDtos createDto) {
        if (createDto == null) return null;
        Problemas entity = new Problemas();
        
        entity.setDesc_problema(createDto.getDescr_problem());
        
        Tickets ticket = new Tickets();
        ticket.setId(createDto.getId_tick());
        entity.setTicket(ticket);
        
        return entity;
    }

    @Override
    public List<ProblemasDtos> toProblemasDtoList(List<Problemas> problemas) {
        if (problemas == null || problemas.isEmpty()) return new ArrayList<>();
        List<ProblemasDtos> dtos = new ArrayList<>();
        
        for (Problemas problema : problemas) {
            dtos.add(toProblemasDto(problema));
        }
        
        return dtos;
    }
}
