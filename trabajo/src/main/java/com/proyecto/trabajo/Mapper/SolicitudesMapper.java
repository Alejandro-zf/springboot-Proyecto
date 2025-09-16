package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;

public interface SolicitudesMapper {
    Solicitudes toEntity(SolicitudesDto dto);
    SolicitudesDto toDTO(Solicitudes entity);
}
