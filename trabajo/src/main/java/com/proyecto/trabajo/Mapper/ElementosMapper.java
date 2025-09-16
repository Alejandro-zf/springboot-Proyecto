package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.models.Elementos;

public interface ElementosMapper {
    Elementos toEntity(ElementoDto dto);
    ElementoDto toDTO(Elementos entity);
}