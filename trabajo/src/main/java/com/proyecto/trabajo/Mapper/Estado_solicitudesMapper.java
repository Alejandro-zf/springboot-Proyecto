package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.Estado_solicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;

public interface Estado_solicitudesMapper {
    Estado_solicitudesDto toDTO(Solicitudes solicitudes);
    void applyEstadoFromDto(Solicitudes solicitudes, Estado_solicitudesDto dto);
}
