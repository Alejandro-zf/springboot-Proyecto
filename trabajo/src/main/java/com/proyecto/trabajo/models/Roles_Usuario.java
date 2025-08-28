package com.proyecto.trabajo.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity

@Embeddable

public class Roles_Usuario {

    public class Roles_Usuarioid implements Serializable{
        private Long id_usuario;
        private Long id_roles;
    }

    @EmbeddedId
    private Roles_Usuarioid id = new Roles_Usuarioid();

    @ManyToOne
    @MapsId("id_usuario")
    private Usuarios usuario;

    
    @ManyToOne
    @MapsId("id_roles")
    private Roles roles;
}
