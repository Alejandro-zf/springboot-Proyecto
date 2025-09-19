package com.proyecto.trabajo.models;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "prestamos_elementos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamos_Elemento {

    @EmbeddedId
    private Prestamos_Elementoid id = new Prestamos_Elementoid();


    @ManyToOne
    @MapsId("prestamosid")
    @JoinColumn(name = "prestamos_id", foreignKey = @ForeignKey(name = "FK_prestamos_elementos_prestamos"))
    private Prestamos prestamos;

    @ManyToOne
    @MapsId("elementoid")
    @JoinColumn(name = "elemento_id", foreignKey = @ForeignKey(name = "FK_prestamos_elementos_elementos"))
    private Elementos elementos;

    @NotNull
    @Column(name = "Obser_prest")
    private String obser_prest;
}
