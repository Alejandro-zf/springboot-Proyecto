package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.UsuariosMapper;
import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.UsuariosRepository;

@Service
public class UsuariosServicesImple implements UsuariosServices {
    private final UsuariosRepository userRepo;
    private final UsuariosMapper usuarioMapper;

    public UsuariosServicesImple(UsuariosRepository userRepo, UsuariosMapper usuarioMapper) {
        this.userRepo = userRepo;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuariosDto guardar(UsuariosDto dto) {
        Usuarios usuarios = usuarioMapper.toUsuarios(dto);
        return usuarioMapper.toUsuariosDto(userRepo.save(usuarios));
    }

    @Override
    public UsuariosDto buscarPorId(Long id) {
        Usuarios usuarios = userRepo.findById(id).orElse(null);
        return usuarioMapper.toUsuariosDto(usuarios);
    }

    @Override
    public List<UsuariosDto> listarTodos() {
        List<Usuarios> usuarios = userRepo.findAll();
        return usuarioMapper.toUsuariosDtoList(usuarios);
    }

    @Override
    public void eliminar(Long id) {
        userRepo.deleteById(id);
    }
}