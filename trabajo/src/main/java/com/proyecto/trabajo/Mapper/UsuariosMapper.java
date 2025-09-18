package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;

public interface UsuariosMapper {
    Usuarios toUsuarios(UsuariosDto usuariosDto);
    UsuariosDto toUsuariosDto(Usuarios usuarios);
}