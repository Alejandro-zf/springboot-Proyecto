package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.UsuariosDto;

public interface UsuariosServices {
    UsuariosDto guardar(UsuariosDto dto);

    UsuariosDto buscarPorId(Long id);

    List<UsuariosDto> listarTodos();

    void eliminar(Long id);

}
