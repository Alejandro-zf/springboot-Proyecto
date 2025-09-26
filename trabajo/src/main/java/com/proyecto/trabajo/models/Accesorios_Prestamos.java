package com.proyecto.trabajo.models;

import jakarta.persistence.Embeddable;
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
@Table(name = "accesorios_prestamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accesorios_Prestamos {

    @EmbeddedId
    private Accesorios_Prestamosid id = new Accesorios_Prestamosid();

    @ManyToOne
    @MapsId("accesoriosid")
    @JoinColumn(name = "accesoriosid", foreignKey = @ForeignKey(name = "Fk_accesorios_prestamos_accesorios"))
    private Accesorios accesorios;

    @ManyToOne
    @MapsId("prestamosid")
    @JoinColumn(name = "prestamosid", foreignKey = @ForeignKey(name = "Fk_accesorios_prestamos_prestamos"))
    private Prestamos prestamos;
}
