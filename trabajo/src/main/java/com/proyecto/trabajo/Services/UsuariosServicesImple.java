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

    public UsuariosServicesImple(UsuariosRepository usuariosRepository, UsuariosMapper usuariosMapper,
            RolesRepository rolesRepository, Roles_UsuarioRepository rolesUsuarioRepository) {
        this.usuariosRepository = usuariosRepository;
        this.usuariosMapper = usuariosMapper;
        this.rolesRepository = rolesRepository;
        this.rolesUsuarioRepository = rolesUsuarioRepository;
    }

    @Override
    public UsuariosDto guardar(com.proyecto.trabajo.dto.UsuariosCreateDto dto) {
        Usuarios usuarios = usuariosMapper.toUsuariosFromCreateDto(dto);
        Usuarios guardado = usuariosRepository.save(usuarios);
        // Asociar rol opcionalmente si viene el id_role
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
        Usuarios datosActualizados = usuariosMapper.toUsuariosFromUpdateDto(dto);
        usuarios.setNom_usu(datosActualizados.getNom_usu());
        usuarios.setApe_usu(datosActualizados.getApe_usu());
        usuarios.setCorreo(datosActualizados.getCorreo());
        usuarios.setPassword(datosActualizados.getPassword());
        Usuarios actualizado = usuariosRepository.save(usuarios);
        return usuariosMapper.toUsuariosDto(actualizado);
    }
}