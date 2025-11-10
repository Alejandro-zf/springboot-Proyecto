package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.EspacioCreateDto;
import com.proyecto.trabajo.dto.EspacioDto;
import com.proyecto.trabajo.models.Espacio;

public interface EspacioMapper {
    Espacio toEspacio(EspacioDto dto);
    EspacioDto toEspacioDto(Espacio entity);
    Espacio toEspacioFromCreateDto(EspacioCreateDto createDto);
    List<EspacioDto> toEspacioDtoList(List<Espacio> espacios);
}
