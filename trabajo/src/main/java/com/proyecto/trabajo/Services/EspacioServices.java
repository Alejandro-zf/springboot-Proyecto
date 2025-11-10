package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.EspacioCreateDto;
import com.proyecto.trabajo.dto.EspacioDto;
import com.proyecto.trabajo.dto.EspacioUpdateDto;

public interface EspacioServices {
    EspacioDto guardar(EspacioCreateDto dto);
    EspacioDto buscarPorId(Integer id);
    List<EspacioDto> listarTodos();
    void eliminar(Integer id);
    EspacioDto actualizarEspacio(Integer id, EspacioUpdateDto dto);
}
