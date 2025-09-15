package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.UsuariosDto;

public interface UsuariosServices {
    public UsuariosDto getUsuario(Long id_usuari);

    public UsuariosDto saveUsuario(UsuariosDto usuariosDto);
 
    public List<UsuariosDto> getAllUsuarios();

    public UsuariosDto deleteUsuarios(Long id_usuari);

}
