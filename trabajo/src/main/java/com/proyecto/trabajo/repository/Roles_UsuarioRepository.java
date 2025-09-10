
package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Roles_Usuario;
import com.proyecto.trabajo.models.Roles_Usuarioid;

public interface Roles_UsuarioRepository extends JpaRepository <Roles_Usuario,Roles_Usuarioid> {
    List<Roles_Usuario> findByRoles_Id (Long rolesid);
    List<Roles_Usuario> findByUsuario_Id (Long usuarioid);

}
