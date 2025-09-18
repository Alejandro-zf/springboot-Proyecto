package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.ElementoDto;

public interface ElementosServices {
    ElementoDto guardar(ElementoDto dto);
    ElementoDto buscarPorId(Long id);
    List<ElementoDto> listarTodos();
    void eliminar(Long id);
    ElementoDto actualizarElemento(ElementoDto dto);
}