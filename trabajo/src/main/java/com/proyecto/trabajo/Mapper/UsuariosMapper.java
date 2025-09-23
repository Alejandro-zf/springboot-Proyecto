package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.dto.UsuariosCreateDto;
import com.proyecto.trabajo.dto.UsuariosUpdateDto;
import com.proyecto.trabajo.models.Usuarios;

public interface UsuariosMapper {
    Usuarios toUsuarios(UsuariosDto usuariosDto);
    UsuariosDto toUsuariosDto(Usuarios usuarios);
    Usuarios toUsuariosFromCreateDto(UsuariosCreateDto createDto);
    Usuarios toUsuariosFromUpdateDto(UsuariosUpdateDto updateDto);
}