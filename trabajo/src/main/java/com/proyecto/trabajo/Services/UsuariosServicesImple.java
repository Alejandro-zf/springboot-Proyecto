package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.UsuariosMapper;
import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.UsuariosRepository;

@Service
public class UsuariosServicesImple implements UsuariosServices {
@Autowired
private UsuariosRepository userRepo;

@Autowired
private UsuariosMapper UsuarioMapper;

@Override
public UsuariosDto getUsuario(Long id_usuari) {
    Usuarios usuarios = userRepo.findById(id_usuari).get();
    return UsuarioMapper.toUsuariosDto(usuarios);
}
@Override
public UsuariosDto saveUsuario(UsuariosDto usuariosDto) {
    Usuarios usuarios = UsuarioMapper.toUsuarios(usuariosDto);
    return UsuarioMapper.toUsuariosDto(userRepo.save(usuarios));
}
@Override
public List<UsuariosDto> getAllUsuarios() {
    List<Usuarios> usuarios = userRepo.findAll();
    return UsuarioMapper.toUsuariosDtoList(usuarios);
}
@Override
public UsuariosDto deleteUsuarios(Long id_usuari) {
    Usuarios usuarios = userRepo.findById(id_usuari).get();
    userRepo.delete(usuarios);
    return UsuarioMapper.toUsuariosDto(usuarios);
}
}