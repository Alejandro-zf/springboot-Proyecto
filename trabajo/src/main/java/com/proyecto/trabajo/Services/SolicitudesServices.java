package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.dto.SolicitudeCreateDto;

public interface SolicitudesServices {
    SolicitudesDto guardar(SolicitudeCreateDto dto);
    SolicitudesDto buscarPorId(Long id);
    List<SolicitudesDto> listarTodos();
    void eliminar(Long id);
    SolicitudesDto actualizarSolicitud(SolicitudesDto dto);
}
