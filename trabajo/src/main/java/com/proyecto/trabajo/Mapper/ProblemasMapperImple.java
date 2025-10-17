package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.models.Problemas;

@Component
public class ProblemasMapperImple implements ProblemasMapper {

    @Override
    public Problemas toProblemas(ProblemasDtos dto) {
        if (dto == null) return null;
        Problemas entity = new Problemas();
        
        entity.setId(dto.getId());
        entity.setDesc_problema(dto.getDescr_problem());
        
        return entity;
    }

    @Override
    public ProblemasDtos toProblemasDto(Problemas entity) {
        if (entity == null) return null;
        ProblemasDtos dto = new ProblemasDtos();
        
        dto.setId(entity.getId());
        dto.setDescr_problem(entity.getDesc_problema());
        
        // Derivar id_tick desde la primera relación si existe
        if (entity.getTickets() != null && !entity.getTickets().isEmpty()) {
            dto.setId_tick(entity.getTickets().get(0).getId());
        }
        
        return dto;
    }

    @Override
    public Problemas toProblemasFromCreateDto(ProblemasCreateDtos createDto) {
        if (createDto == null) return null;
        Problemas entity = new Problemas();
        
        entity.setDesc_problema(createDto.getDescr_problem());
        
        return entity;
    }

    @Override
    public List<ProblemasDtos> toProblemasDtoList(List<Problemas> problemas) {
        if (problemas == null) {
            return List.of();
        }
        
        List<ProblemasDtos> problemasDtos = new ArrayList<>(problemas.size());
        for (Problemas problema : problemas) {
            problemasDtos.add(toProblemasDto(problema));
        }
        
        return problemasDtos;
    }
}
