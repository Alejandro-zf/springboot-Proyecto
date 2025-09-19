package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.SolicitudesDto;

public interface SolicitudesServices {
    SolicitudesDto guardar(SolicitudesDto dto);
    SolicitudesDto buscarPorId(Long id);
    List<SolicitudesDto> listarTodos();
    void eliminar(Long id);
    SolicitudesDto actualizarSolicitud(SolicitudesDto dto);
}
