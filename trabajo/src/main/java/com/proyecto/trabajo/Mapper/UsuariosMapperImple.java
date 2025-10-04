package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.dto.UsuariosCreateDto;
import com.proyecto.trabajo.dto.UsuariosUpdateDto;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Tip_documento;
import com.proyecto.trabajo.repository.Tip_documentoRepository;
import com.proyecto.trabajo.models.Roles_Usuario;

import jakarta.persistence.EntityNotFoundException;

@Component
public class UsuariosMapperImple implements UsuariosMapper {
    private final Tip_documentoRepository tipDocumentoRepository;

    public UsuariosMapperImple(Tip_documentoRepository tipDocumentoRepository) {
        this.tipDocumentoRepository = tipDocumentoRepository;
    }

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
        if (usuariosDto.getId_tip_docu() != null) {
            Tip_documento tip = tipDocumentoRepository.findById(usuariosDto.getId_tip_docu())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
            usuarios.setTip_documento(tip);
        }
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
        if (usuarios.getTip_documento() != null) {
            usuariosDto.setId_tip_docu(usuarios.getTip_documento().getId());
            usuariosDto.setTip_docu(usuarios.getTip_documento().getTipo_doc());
        }
        if (usuarios.getRole() != null && !usuarios.getRole().isEmpty()) {
            Roles_Usuario ru = usuarios.getRole().get(0);
            if (ru.getRoles() != null) {
                usuariosDto.setId_rol(ru.getRoles().getId());
                usuariosDto.setNomb_rol(ru.getRoles().getNom_rol());
            }
        }
        return usuariosDto;
    }

    @Override
    public Usuarios toUsuariosFromCreateDto(UsuariosCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Usuarios usuarios = new Usuarios();
    usuarios.setNom_usu(createDto.getNom_su());
    usuarios.setApe_usu(createDto.getApe_su());
    usuarios.setCorreo(createDto.getCorre());
    usuarios.setNum_doc(createDto.getNum_docu());
    usuarios.setPassword(createDto.getPasword());
    usuarios.setEstado(createDto.getEstad());
    // Tip_documento es obligatorio
        if (createDto.getTip_docu() == null) {
            throw new EntityNotFoundException("El id del tipo de documento es obligatorio");
        }
        Tip_documento tip = tipDocumentoRepository.findById(createDto.getTip_docu().byteValue())
            .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
        usuarios.setTip_documento(tip);
        return usuarios;
    }

    @Override
    public Usuarios toUsuariosFromUpdateDto(UsuariosUpdateDto updateDto) {
        if (updateDto == null) {
            return null;
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setId(updateDto.getId_Usu());
        usuarios.setNom_usu(updateDto.getNom_us());
        usuarios.setApe_usu(updateDto.getApe_us());
        usuarios.setCorreo(updateDto.getCorre());
        usuarios.setPassword(updateDto.getPassword());
        usuarios.setEstado(updateDto.getEst_usu());
        return usuarios;
    }
}