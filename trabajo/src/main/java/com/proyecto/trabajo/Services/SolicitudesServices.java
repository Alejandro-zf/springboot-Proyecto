package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.dto.SolicitudeCreateDto;
import com.proyecto.trabajo.dto.SolicitudesUpdateDtos;

public interface SolicitudesServices {
    SolicitudesDto guardar(SolicitudeCreateDto dto);
    SolicitudesDto buscarPorId(Long id);
    List<SolicitudesDto> listarTodos();
    void eliminar(Long id);
    SolicitudesDto actualizarSolicitud(Long id, SolicitudesUpdateDtos dto);
    void expirarSolicitudesVencidas();
}