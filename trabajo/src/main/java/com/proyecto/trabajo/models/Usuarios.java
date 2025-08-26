package com.proyecto.trabajo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;
    @Column(nullable=false,length=50)
    private String nom_usu;
    @Column(nullable=false,length=50)
    private String ape_usu;
    @Column(nullable=false,length=100)
    private String correo;

    private Integer num_doc;
    @Column(nullable=false,length=30)
    private String password;
}
