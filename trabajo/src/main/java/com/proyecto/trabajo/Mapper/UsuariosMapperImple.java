package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;

@Component
public class UsuariosMapperImple implements UsuariosMapper {

    @Override
    public Usuarios toEntity(UsuariosDto dto) {
        if (dto == null) {
            return null;
        }
        Usuarios entity = new Usuarios();
        entity.setId(dto.getId_usuari());
        entity.setNom_usu(dto.getNom_usua());
        entity.setApe_usu(dto.getApe_usua());
        entity.setCorreo(dto.getCorre());
        entity.setNum_doc(dto.getNum_docu());
        entity.setPassword(dto.getPasswor());
        return entity;
    }

    @Override
    public UsuariosDto toDTO(Usuarios entity) {
        if (entity == null) {
            return null;
        }
        UsuariosDto dto = new UsuariosDto();
        dto.setId_usuari(entity.getId());
        dto.setNom_usua(entity.getNom_usu());
        dto.setApe_usua(entity.getApe_usu());
        dto.setCorre(entity.getCorreo());
        dto.setNum_docu(entity.getNum_doc());
        dto.setPasswor(entity.getPassword());
        return dto;
    }
}