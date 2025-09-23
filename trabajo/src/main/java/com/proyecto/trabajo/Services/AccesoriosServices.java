package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.AccesoriosDto;

public interface AccesoriosServices {
    AccesoriosDto guardar(AccesoriosDto dto);
    AccesoriosDto buscarPorId(Integer id);
    List<AccesoriosDto> listarTodos();
    void eliminar(Integer id);
    AccesoriosDto actualizarAccesorio(AccesoriosDto dto);
}
