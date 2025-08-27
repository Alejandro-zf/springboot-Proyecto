package com.proyecto.trabajo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Solicitudes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_solicitud;
    private Integer cantidad;

    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;
    @Column(nullable = false,length = 25)
    private String ambiente;
    @Column(nullable = false,length = 25)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_usuari")
    private Usuarios usuario;
}
