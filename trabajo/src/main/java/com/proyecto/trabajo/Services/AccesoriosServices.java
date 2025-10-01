package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.AccesoriosDto;
import com.proyecto.trabajo.dto.AccesoriosCreateDtos;

public interface AccesoriosServices {
    AccesoriosDto guardar(AccesoriosCreateDtos dto);
    AccesoriosDto buscarPorId(Integer id);
    List<AccesoriosDto> listarTodos();
    void eliminar(Integer id);
    AccesoriosDto actualizarAccesorio(AccesoriosDto dto);
}
