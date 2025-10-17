package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.models.Problemas;

public interface ProblemasMapper {
    Problemas toProblemas(ProblemasDtos dto);
    ProblemasDtos toProblemasDto(Problemas entity);
    Problemas toProblemasFromCreateDto(ProblemasCreateDtos createDto);
    List<ProblemasDtos> toProblemasDtoList(List<Problemas> problemas);
}
