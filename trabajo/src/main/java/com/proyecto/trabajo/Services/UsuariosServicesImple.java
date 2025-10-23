package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.UsuariosMapper;
import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.RolesRepository;
import com.proyecto.trabajo.repository.Roles_UsuarioRepository;
import com.proyecto.trabajo.models.Roles;
import com.proyecto.trabajo.models.Roles_Usuario;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuariosServicesImple implements UsuariosServices {

    private final UsuariosRepository usuariosRepository;
    private final UsuariosMapper usuariosMapper;
    private final RolesRepository rolesRepository;
    private final Roles_UsuarioRepository rolesUsuarioRepository;

    public UsuariosServicesImple(UsuariosRepository usuariosRepository,
                                 UsuariosMapper usuariosMapper,
                                 RolesRepository rolesRepository,
                                 Roles_UsuarioRepository rolesUsuarioRepository) {
        this.usuariosRepository = usuariosRepository;
        this.usuariosMapper = usuariosMapper;
        this.rolesRepository = rolesRepository;
        this.rolesUsuarioRepository = rolesUsuarioRepository;
    }

    public UsuariosDto guardar(com.proyecto.trabajo.dto.UsuariosCreateDto dto) {
        Usuarios usuarios = usuariosMapper.toUsuariosFromCreateDto(dto);

        usuarios.setEstado((byte) 1);

        if (usuarios.getTip_documento() == null) {
            throw new IllegalArgumentException("El campo tip_document (tipo de documento) es obligatorio");
        }

        Usuarios guardado = usuariosRepository.save(usuarios);
        if (dto.getId_role() != null) {
            Roles rol = rolesRepository.findById(dto.getId_role())
                    .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
            Roles_Usuario ru = new Roles_Usuario();
            ru.setUsuario(guardado);
            ru.setRoles(rol);
            rolesUsuarioRepository.save(ru);
            guardado.getRole().add(ru);
        }
        return usuariosMapper.toUsuariosDto(guardado);
    }
    @Override
    public UsuariosDto buscarPorId(Long id) {
        return usuariosRepository.findById(id)
                .map(usuariosMapper::toUsuariosDto)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    @Override
    public List<UsuariosDto> listarTodos() {
        return usuariosRepository.findAll()
                .stream()
                .map(usuariosMapper::toUsuariosDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        usuariosRepository.deleteById(id);
    }

    @Override
    public UsuariosDto actualizarUsuario(Long id, com.proyecto.trabajo.dto.UsuariosUpdateDto dto) {
        Usuarios usuarios = usuariosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (dto.getNom_us() != null) {
            usuarios.setNom_usu(dto.getNom_us());
        }
        if (dto.getApe_us() != null) {
            usuarios.setApe_usu(dto.getApe_us());
        }
        if (dto.getCorre() != null) {
            usuarios.setCorreo(dto.getCorre());
        }
        if (dto.getPassword() != null) {
            usuarios.setPassword(dto.getPassword());
        }
        if (dto.getEst_usu() != null) {
            Byte est = dto.getEst_usu();
            if (est < 1 || est > 2) {
                throw new IllegalArgumentException("Estado inválido. Debe ser 1 (activado) o 2 (desactivado)");
            }
            usuarios.setEstado(est);
        }

        // Actualizar el rol si id_rl está presente
        if (dto.getId_rl() != null) {
            // Eliminar roles actuales y asignar el nuevo rol
            usuarios.getRole().clear();
            Roles rol = rolesRepository.findById(dto.getId_rl())
                    .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
            Roles_Usuario ru = new Roles_Usuario();
            ru.setUsuario(usuarios);
            ru.setRoles(rol);
            rolesUsuarioRepository.save(ru);
            usuarios.getRole().add(ru);
        }

        Usuarios actualizado = usuariosRepository.save(usuarios);
        return usuariosMapper.toUsuariosDto(actualizado);
    }
}