package com.proyecto.trabajo.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Estado_solicitudes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_estado_soli;

    @Column(nullable = false,length = 25)
    private String tipo_estado;

    @OneToMany(mappedBy = "estado_solicitudes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Solicitudes> solicitudes = new ArrayList<>();
}
