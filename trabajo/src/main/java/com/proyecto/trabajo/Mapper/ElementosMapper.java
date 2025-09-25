package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementosCreateDto;
import com.proyecto.trabajo.models.Elementos;

public interface ElementosMapper {
    Elementos toElementos(ElementoDto elementoDto);
    ElementoDto toElementoDto(Elementos elementos);
    Elementos toElementosFromCreateDto(ElementosCreateDto createDto);
}