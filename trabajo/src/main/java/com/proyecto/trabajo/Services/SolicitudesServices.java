package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.dto.SolicitudesDto;

public interface SolicitudesServices {
    SolicitudesDto guardar(SolicitudesDto dto);
    SolicitudesDto buscarPorId(Long id);
    java.util.List<SolicitudesDto> listarTodos();
    void eliminar(Long id);
}