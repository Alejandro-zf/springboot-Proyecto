package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.Accesorios_SolicitudesDtos;
import com.proyecto.trabajo.models.Accesorios_solicitudes;

public interface Accesorios_SolicitudesMapper {

    Accesorios_solicitudes toEntity(Accesorios_SolicitudesDtos dto);

    Accesorios_SolicitudesDtos toDTO(Accesorios_solicitudes entity);
}
