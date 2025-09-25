package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.dto.PrestamosCreateDto;

public interface PrestamosServices {
    PrestamosDto guardar(PrestamosCreateDto dto);
    PrestamosDto buscarPorId(Long id);
    List<PrestamosDto> listarTodos();
    void eliminar(Long id);
    PrestamosDto actualizarPrestamo(PrestamosDto dto);
}
