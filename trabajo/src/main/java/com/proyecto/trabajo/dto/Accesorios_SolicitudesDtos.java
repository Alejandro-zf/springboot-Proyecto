package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Accesorios_SolicitudesDtos {
    private Long id_acces;
    private String nom_acces;

    private Long id_soli;
}
