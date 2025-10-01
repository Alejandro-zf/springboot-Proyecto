package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.Accesorios_SolicitudesDtos;

public interface Accesorios_SolicitudesService {
    Accesorios_SolicitudesDtos asignar(Accesorios_SolicitudesDtos dto);
    List<Accesorios_SolicitudesDtos> listarPorSolicitud(Long solicitudId);
    List<Accesorios_SolicitudesDtos> listarPorAccesorio(Long accesorioId);
    void eliminarAsignacion(Long solicitudId, Long accesorioId);
    List<Accesorios_SolicitudesDtos> asignarAccesorios(List<Accesorios_SolicitudesDtos> dtos);
}
