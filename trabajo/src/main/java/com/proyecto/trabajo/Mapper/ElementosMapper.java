package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.models.Elementos;

public interface ElementosMapper {
    Elementos toElementos(ElementoDto elementoDto);
    ElementoDto toElementoDto(Elementos elementos);
    List<ElementoDto> toElementoDtoList(List<Elementos> elementos);
}
