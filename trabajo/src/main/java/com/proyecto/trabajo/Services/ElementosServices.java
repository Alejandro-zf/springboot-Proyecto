package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.dto.ElementoDto;

public interface ElementosServices {
    ElementoDto guardar(ElementoDto dto);
    ElementoDto buscarPorId(Long id);
    java.util.List<ElementoDto> listarTodos();
    void eliminar(Long id);
}