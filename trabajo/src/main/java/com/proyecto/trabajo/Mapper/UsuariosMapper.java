package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;

public interface UsuariosMapper {
Usuarios toUsuarios(UsuariosDto usuariosDto);
UsuariosDto toUsuariosDto(Usuarios usuarios);
List<UsuariosDto> toUsuariosDtoList(List<Usuarios> usuarioss);

}
