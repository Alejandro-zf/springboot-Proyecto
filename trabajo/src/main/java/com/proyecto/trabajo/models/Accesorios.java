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
public class Accesorios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_accesorios;
    private Integer cant;
    @Column(nullable = false,length = 30)
    private String nom_acce;
    @Column(nullable = false,length = 20)
    private String marca;
    private Integer num_serie;
}
