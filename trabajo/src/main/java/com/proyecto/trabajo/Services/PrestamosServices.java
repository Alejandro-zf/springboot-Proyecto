package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.dto.PrestamosDto;

public interface PrestamosServices {
    PrestamosDto guardar(PrestamosDto dto);
    PrestamosDto buscarPorId(Long id);
    java.util.List<PrestamosDto> listarTodos();
    void eliminar(Long id);
}

//asi ses