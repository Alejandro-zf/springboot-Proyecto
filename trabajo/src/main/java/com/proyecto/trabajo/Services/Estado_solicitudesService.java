package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.dto.Estado_solicitudesDto;

public interface Estado_solicitudesService {
    Estado_solicitudesDto actualizarEstado(Estado_solicitudesDto dto);
    Estado_solicitudesDto obtenerEstado(Long solicitudId);
}
