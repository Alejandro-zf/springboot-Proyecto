package com.proyecto.trabajo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Prestamos_Elemento {

    @Embeddable
    public class Prestamos_Elementoid implements Serializable{
    
    private Long id_prestamo;
    private Long id_elemento;
    }

    @Column(nullable = false, length = 255)
    private String  Obser_prest;

    @EmbeddedId
    private Prestamos_Elementoid id = new Prestamos_Elementoid();

    @ManyToOne
    @MapsId("id_prestamo")
    private Prestamos prestamos;

    @ManyToOne
    @MapsId("id_elemento")
    private Elementos elementos;
}
