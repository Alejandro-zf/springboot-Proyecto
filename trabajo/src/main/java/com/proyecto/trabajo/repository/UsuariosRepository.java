package com.proyecto.trabajo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Usuarios;
import java.util.Optional;

public interface UsuariosRepository extends JpaRepository <Usuarios,Long>{
	Optional<Usuarios> findByCorreo(String correo);
	Optional<Usuarios> findByResetToken(String resetToken);

}
