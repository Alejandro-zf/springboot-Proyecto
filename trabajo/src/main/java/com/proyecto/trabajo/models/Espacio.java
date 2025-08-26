package com.proyecto.trabajo.models;

import java.time.LocalDateTime;

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
public class Espacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_espacio;
    @Column(nullable = false,length = 25)
    private String nom_espa;
    
    private LocalDateTime tiem_uso;
    private LocalDateTime  hora_soli;
    private Integer num_ficha;

}
