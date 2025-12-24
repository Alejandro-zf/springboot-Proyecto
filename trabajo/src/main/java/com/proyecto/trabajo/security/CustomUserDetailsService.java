package com.proyecto.trabajo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.UsuariosRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario por correo con repositorio
        Usuarios usuario = usuariosRepository
                .findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Obtener los roles del usuario
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        if (usuario.getRole() != null && !usuario.getRole().isEmpty()) {
            usuario.getRole().forEach(rolesUsuario -> {
                String roleName = "ROLE_" + rolesUsuario.getRoles().getNom_rol();
                authorities.add(new SimpleGrantedAuthority(roleName));
            });
        }

        
        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(usuario.getEstado() == 0)
                .credentialsExpired(false)
                .disabled(usuario.getEstado() == 0)
                .build();
    }
}
