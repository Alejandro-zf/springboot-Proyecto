package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.TrasabilidadDtos;
import com.proyecto.trabajo.dto.TrasabilidadCreateDtos;
import com.proyecto.trabajo.models.Trasabilidad;

public interface TrasabilidadMapper {
    Trasabilidad toTrasabilidad(TrasabilidadDtos dto);
    TrasabilidadDtos toTrasabilidadDto(Trasabilidad entity);
    Trasabilidad toTrasabilidadFromCreateDto(TrasabilidadCreateDtos createDto);
}
