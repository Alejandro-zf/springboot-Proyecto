package com.proyecto.trabajo.models;

import java.io.Serializable;

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
public class Elemento_Solicitudes {

    @Embeddable
    public class Elemento_Solicitudesid implements Serializable{

    private Long id_solicitud;
    private Long id_elemento;
    
    }
    
    @EmbeddedId
    private Elemento_Solicitudesid id = new Elemento_Solicitudesid();

    
    @ManyToOne
    @MapsId("id_solicitud")
    private Solicitudes solicitudes;

    
    @ManyToOne
    @MapsId("id_elemento")
    private Elementos elementos;
}
