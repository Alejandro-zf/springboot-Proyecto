package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementosCreateDto;
import com.proyecto.trabajo.dto.ElementoUpdateDtos;

public interface ElementosServices {
    ElementoDto guardar(ElementosCreateDto dto);
    ElementoDto buscarPorId(Long id);
    List<ElementoDto> listarTodos();
    void eliminar(Long id);
    ElementoDto actualizarElemento(Long id, ElementoUpdateDtos dto);
}
