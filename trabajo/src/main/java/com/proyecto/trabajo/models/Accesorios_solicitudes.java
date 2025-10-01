package com.proyecto.trabajo.models;

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
@Table(name = "accesorios_solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Accesorios_solicitudes {

    @EmbeddedId
    private Accesorios_solicitudesid id = new Accesorios_solicitudesid();

    @ManyToOne
    @MapsId("solicitudid")
    @JoinColumn(name = "solicitud_id", foreignKey = @ForeignKey(name = "FK_accesorios_solicitudes_solicitudes"))
    private Solicitudes solicitudes;

    @ManyToOne
    @MapsId("accesorioid")
    @JoinColumn(name = "accesorio_id", foreignKey = @ForeignKey(name = "FK_accesorios_solicitudes_accesorios"))
    private Accesorios accesorios;

}
