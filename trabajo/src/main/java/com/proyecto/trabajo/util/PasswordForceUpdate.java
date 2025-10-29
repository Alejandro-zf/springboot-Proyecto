package com.proyecto.trabajo.util;

import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// ⚠️ DESACTIVADO: Este componente actualiza contraseñas específicas al iniciar
// Solo activar temporalmente si necesitas resetear una contraseña
// Para activar, descomenta la línea @Component
//@Component
public class PasswordForceUpdate implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("⚠️ PasswordForceUpdate está DESACTIVADO");
        System.out.println("   Para activarlo, descomenta @Component en PasswordForceUpdate.java");
        
        // Código original comentado para evitar ejecución accidental
        /*
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
        */
    }
}
