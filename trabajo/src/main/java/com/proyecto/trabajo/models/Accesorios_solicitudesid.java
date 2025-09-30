package com.proyecto.trabajo.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Accesorios_solicitudesid implements Serializable {

    private Long solicitudid;
    private Integer accesorioid;
    
}
