package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.Elemento_SolicitudesDtos;

public interface Elemento_SolicitudesService {
    Elemento_SolicitudesDtos asignar(Elemento_SolicitudesDtos dto);
    List<Elemento_SolicitudesDtos> listarPorSolicitud(Long solicitudId);
    List<Elemento_SolicitudesDtos> listarPorElemento(Long elementoId);
    void eliminarAsignacion(Long solicitudId, Long elementoId);
    List<Elemento_SolicitudesDtos> asignarElementos(List<Elemento_SolicitudesDtos> dtos);
}
