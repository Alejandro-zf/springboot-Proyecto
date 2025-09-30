package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.Accesorios_PrestamosDtos;

public interface Accesorios_PrestamosService {
    Accesorios_PrestamosDtos asignar(Accesorios_PrestamosDtos dto);
    List<Accesorios_PrestamosDtos> listarPorPrestamo(Long prestamosId);
    List<Accesorios_PrestamosDtos> listarPorAccesorio(Long accesorioId);
    void eliminarAsignacion(Long accesorioId, Long prestamosId);
    List<Accesorios_PrestamosDtos> asignarAccesorios(List<Accesorios_PrestamosDtos> dtos);
}
