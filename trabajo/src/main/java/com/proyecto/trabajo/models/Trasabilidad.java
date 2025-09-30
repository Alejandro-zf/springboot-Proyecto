package com.proyecto.trabajo.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter

public class Trasabilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private LocalDate fecha;
    private String Observacion;

    @ManyToOne
    @JoinColumn(name = "Id_usuario", nullable = false, foreignKey = @ForeignKey(name = "FK_id_usuario"))
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "Id_ticket", nullable = false, foreignKey = @ForeignKey(name = "FK_id_ticket"))
    private Tickets tickets;
}
