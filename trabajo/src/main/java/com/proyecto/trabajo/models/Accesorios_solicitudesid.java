package com.proyecto.trabajo.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Accesorios_solicitudesid {

    private Long solicitudid;
    private Long accesorioid;
    
}
