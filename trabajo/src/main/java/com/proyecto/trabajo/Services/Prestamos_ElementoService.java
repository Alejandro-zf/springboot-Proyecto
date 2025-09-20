package com.proyecto.trabajo.Services;

import java.util.List;
import com.proyecto.trabajo.dto.Prestamos_ElementoDto;

public interface Prestamos_ElementoService {
    Prestamos_ElementoDto asignar(Prestamos_ElementoDto dto);
    List<Prestamos_ElementoDto> listarPorPrestamo(Long prestamosId);
    List<Prestamos_ElementoDto> listarPorElemento(Long elementoId);
    void eliminarAsignacion(Long prestamosId, Long elementoId);
    List<Prestamos_ElementoDto> asignarElementos(List<Prestamos_ElementoDto> dtos);
}
