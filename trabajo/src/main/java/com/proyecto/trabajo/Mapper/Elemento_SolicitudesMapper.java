package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.Elemento_SolicitudesDtos;
import com.proyecto.trabajo.models.Elemento_Solicitudes;

public interface Elemento_SolicitudesMapper {

    Elemento_Solicitudes toElemento_Solicitudes(Elemento_SolicitudesDtos dto);

    Elemento_SolicitudesDtos toDTO(Elemento_Solicitudes entity);
}
