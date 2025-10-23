package com.proyecto.trabajo.models;

import java.util.ArrayList;
import java.util.List;
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
    private Long id;
    @Column(nullable = false, length = 100)
    private String nom_elemento;
    @Column(nullable = false, length = 150)
    private String obser;

    @Column(nullable = false)
    private Byte estadosoelement = 1; // 1=Activo, 0=Inactivo

    private Integer num_serie;
    
    @Column(nullable = true, length = 255)
    private String componentes;
    
        @Column(length = 50)
        private String marca;

    @OneToMany(mappedBy = "elementos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Elemento_Solicitudes> solicitud = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sub_categoria", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_subcategoria"))
    private Sub_categoria sub_categoria;

    @OneToMany(mappedBy = "elementos")
    private List <Prestamos_Elemento> prestamosselemen;

    @OneToMany(mappedBy = "elementos")
    private List<Tickets> tickets;
}
