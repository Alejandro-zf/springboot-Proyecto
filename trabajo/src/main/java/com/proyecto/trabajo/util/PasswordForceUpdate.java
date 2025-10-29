package com.proyecto.trabajo.util;

import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordForceUpdate implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String correo = "alejandro@example.com";
        String nuevaPassword = "adri123";
        Usuarios usuario = usuariosRepository.findByCorreo(correo).orElse(null);
        if (usuario != null) {
            usuario.setPassword(passwordEncoder.encode(nuevaPassword));
            usuariosRepository.save(usuario);
            System.out.println("Contraseña actualizada y cifrada para: " + correo);
        } else {
            System.out.println("Usuario no encontrado: " + correo);
        }
    }
}
