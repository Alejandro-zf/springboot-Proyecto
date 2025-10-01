package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.dto.SolicitudeCreateDto;
import com.proyecto.trabajo.models.Solicitudes;

public interface SolicitudesMapper {

    Solicitudes toSolicitudes(SolicitudesDto solicitudesDto);
    SolicitudesDto toSolicitudesDto(Solicitudes solicitudes);
    Solicitudes toSolicitudesFromCreateDto(SolicitudeCreateDto createDto);

}