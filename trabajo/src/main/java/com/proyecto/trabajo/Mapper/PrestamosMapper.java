package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.dto.PrestamosCreateDto;

public interface PrestamosMapper {
    Prestamos toPrestamos(PrestamosDto prestamosDto);
    PrestamosDto toPrestamosDto(Prestamos prestamos);
    Prestamos toPrestamosFromCreateDto(PrestamosCreateDto createDto);
        Prestamos fromSolicitudAprobada(com.proyecto.trabajo.models.Solicitudes solicitud);

}