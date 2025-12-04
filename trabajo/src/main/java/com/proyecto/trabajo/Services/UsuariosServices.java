package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.UsuariosCreateDto;
import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.dto.UsuariosUpdateDto;

public interface UsuariosServices {
    UsuariosDto guardar(UsuariosCreateDto dto);
    UsuariosDto buscarPorId(Long id);
    List<UsuariosDto> listarTodos();
    void eliminar(Long id);
    UsuariosDto actualizarUsuario(Long id, UsuariosUpdateDto dto);
    UsuariosDto actualizarMiPerfil(String correoAutenticado, UsuariosUpdateDto dto);
    byte[] generarPlantillaUsuarios() throws Exception;
}
