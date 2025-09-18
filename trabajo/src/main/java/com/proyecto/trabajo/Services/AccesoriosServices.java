package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.dto.AccesoriosDto;

public interface AccesoriosServices {
    AccesoriosDto guardar(AccesoriosDto dto);
    AccesoriosDto buscarPorId(Integer id);
    java.util.List<AccesoriosDto> listarTodos();
    void eliminar(Integer id);
}