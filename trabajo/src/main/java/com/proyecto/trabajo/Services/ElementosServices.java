package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementosCreateDto;

public interface ElementosServices {
    ElementoDto guardar(ElementosCreateDto dto);
    ElementoDto buscarPorId(Long id);
    List<ElementoDto> listarTodos();
    void eliminar(Long id);
    ElementoDto actualizarElemento(ElementoDto dto);
}
