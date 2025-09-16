package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;

public interface UsuariosMapper {
    Usuarios toEntity(UsuariosDto dto);
    UsuariosDto toDTO(Usuarios entity);
}