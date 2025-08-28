package com.proyecto.trabajo.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id_usuario", "id_roles"})

public class Roles_Usuarioid implements Serializable {


    public Roles_Usuarioid (Integer id, Integer id2) {

    }
    private Long id_usuario;
    private Long id_roles;
}
