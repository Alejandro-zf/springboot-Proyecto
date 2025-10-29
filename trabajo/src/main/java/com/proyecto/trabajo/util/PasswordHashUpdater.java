package com.proyecto.trabajo.util;

import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashUpdater implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        for (Usuarios usuario : usuariosRepository.findAll()) {
            String password = usuario.getPassword();
            if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                usuario.setPassword(passwordEncoder.encode(password));
                usuariosRepository.save(usuario);
                System.out.println("Contraseña actualizada para usuario: " + usuario.getCorreo());
            }
        }
    }
}
