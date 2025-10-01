package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.UsuariosMapper;
import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.UsuariosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuariosServicesImple implements UsuariosServices {

    private final UsuariosRepository usuariosRepository;
    private final UsuariosMapper usuariosMapper;

    public UsuariosServicesImple(UsuariosRepository usuariosRepository, UsuariosMapper usuariosMapper) {
        this.usuariosRepository = usuariosRepository;
        this.usuariosMapper = usuariosMapper;
    }

    @Override
    public UsuariosDto guardar(com.proyecto.trabajo.dto.UsuariosCreateDto dto) {
        Usuarios usuarios = usuariosMapper.toUsuariosFromCreateDto(dto);
        Usuarios guardado = usuariosRepository.save(usuarios);
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