package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.PrestamosDto;

public interface PrestamosServices {
    PrestamosDto guardar(PrestamosDto dto);

    PrestamosDto buscarPorId(Long id);

    List<PrestamosDto> listarTodos();

    void eliminar(Long id);
}
