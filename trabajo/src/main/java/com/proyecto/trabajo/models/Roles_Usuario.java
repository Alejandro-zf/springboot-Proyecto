package com.proyecto.trabajo.models;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles_usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Roles_Usuario {

    @EmbeddedId
    private Roles_Usuarioid id = new Roles_Usuarioid();

    @ManyToOne
    @MapsId("usuarioid")
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "FK_usuario_roles_usuario"))
    private Usuarios usuario;

    
    @ManyToOne
    @MapsId("rolesid")
    @JoinColumn(name = "roles_id", foreignKey = @ForeignKey(name = "FK_roles_usuario_roles"))
    private Roles roles;
}
