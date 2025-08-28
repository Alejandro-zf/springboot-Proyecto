package com.proyecto.trabajo.models;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.internal.ForeignKeys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Elementos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_elemento;
    @Column(nullable = false, length = 30)
    private String nom_elemento;
    @Column(nullable = false, length = 150)
    private String obser;

    private Integer num_serie;
    
    @Column(nullable = false, length = 25)
    private String componentes;

    @OneToMany(mappedBy = "elementos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Elemento_Solicitudes> solicitud = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "categoria", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_categoria"))
    private Categoria categoria;

    @OneToMany(mappedBy = "elementos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Tickets_elemento> element = new ArrayList<>();

    @OneToMany(mappedBy = "elementos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Prestamos_Elemento> prestamo =new ArrayList<>();
}
