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
    // El DTO UsuariosDto no tiene campo password, así que se omite
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
    // El DTO UsuariosDto no tiene campo password, así que se omite
        return usuariosDto;
    }

    @Override
    public Usuarios toUsuariosFromCreateDto(com.proyecto.trabajo.dto.UsuariosCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Usuarios usuarios = new Usuarios();
    usuarios.setNom_usu(createDto.getNom_su());
    usuarios.setApe_usu(createDto.getApe_su());
    usuarios.setCorreo(createDto.getCorre());
    usuarios.setNum_doc(createDto.getNum_docu());
    usuarios.setPassword(createDto.getPasword());
        return usuarios;
    }

    @Override
    public Usuarios toUsuariosFromUpdateDto(com.proyecto.trabajo.dto.UsuariosUpdateDto updateDto) {
        if (updateDto == null) {
            return null;
        }
        Usuarios usuarios = new Usuarios();
    usuarios.setId(updateDto.getId_Usu());
    usuarios.setNom_usu(updateDto.getNom_us());
    usuarios.setApe_usu(updateDto.getApe_us());
    usuarios.setCorreo(updateDto.getCorre());
    usuarios.setPassword(updateDto.getPassword());
        return usuarios;
    }
}