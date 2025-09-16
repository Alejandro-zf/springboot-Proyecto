package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.models.Elementos;

public interface ElementosMapper {
    // Estilo base: toEntity / toDTO
    Elementos toEntity(ElementoDto dto);
    ElementoDto toDTO(Elementos entity);
}