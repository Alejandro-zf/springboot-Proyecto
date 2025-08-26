package com.proyecto.trabajo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity

public class Elementos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_elemento;
    @Column(nullable = false, length = 5)
    private String nom_elemento;
    private String obser;
    private Integer num_serie;
    @Column(nullable = false, length = 10)
    private String id_acceso;
    @Column(nullable = false, length = 5)
    private String id_catego;
    @Column(nullable = false, length = 5)
    private String componentes;
}
