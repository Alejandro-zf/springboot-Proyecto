package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;

public interface SolicitudesMapper {

    Solicitudes toSolicitudes(SolicitudesDto dto);

    SolicitudesDto toDTO(Solicitudes solicitudes);

    // Método para compatibilidad con ServicesImple
    default SolicitudesDto toSolicitudesDto(Solicitudes solicitudes) {
        return toDTO(solicitudes);
    }

}