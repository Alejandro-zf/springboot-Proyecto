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
@Table(name = "elemento_solicitudes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Elemento_Solicitudes {

    @EmbeddedId
    private Elemento_Solicitudesid id = new Elemento_Solicitudesid();

    
    @ManyToOne
    @MapsId("solicitudid")
    @JoinColumn(name = "solicitud_id", foreignKey = @ForeignKey(name = "FK_elemento_solicitudes_solicitudes"))
    private Solicitudes solicitudes;


    @ManyToOne
    @MapsId("elementoid")
    @JoinColumn(name = "elemento_id", nullable = true, foreignKey = @ForeignKey(name = "FK_elemento_solicitudes_elementos"))
    private Elementos elementos;
}
