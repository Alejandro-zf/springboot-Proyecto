package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;

@Component
public class UsuariosMapperImple implements UsuariosMapper {

    @Override
    public Usuarios toUsuarios(UsuariosDto usuariosDto) {
        if (usuariosDto == null) {
            return null;
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setId(usuariosDto.getId_usuari());
        usuarios.setNom_usu(usuariosDto.getNom_usua());
        usuarios.setApe_usu(usuariosDto.getApe_usua());
        usuarios.setCorreo(usuariosDto.getCorre());
        usuarios.setNum_doc(usuariosDto.getNum_docu());
        usuarios.setPassword(usuariosDto.getPasswor());
        return usuarios;
    }

    @Override
    public UsuariosDto toUsuariosDto(Usuarios usuarios) {
        if (usuarios == null) {
            return null;
        }
        UsuariosDto usuariosDto = new UsuariosDto();
        usuariosDto.setId_usuari(usuarios.getId());
        usuariosDto.setNom_usua(usuarios.getNom_usu());
        usuariosDto.setApe_usua(usuarios.getApe_usu());
        usuariosDto.setCorre(usuarios.getCorreo());
        usuariosDto.setNum_docu(usuarios.getNum_doc());
        usuariosDto.setPasswor(usuarios.getPassword());
        return usuariosDto;
    }
}