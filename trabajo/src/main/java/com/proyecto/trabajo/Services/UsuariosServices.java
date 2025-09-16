package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.dto.UsuariosDto;

public interface UsuariosServices {
    UsuariosDto guardar(UsuariosDto dto);
    UsuariosDto buscarPorId(Long id);
    java.util.List<UsuariosDto> listarTodos();
    void eliminar(Long id);
}