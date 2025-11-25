package com.proyecto.trabajo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrasabilidadDtos {
    private Long id_trsa;
    private LocalDate fech;
    private String obser;

    private Long id_usu;
    private String nom_us;

    private Long id_ticet;

    private Long id_elemen;
    private String nom_elemen;
    private String obse;


}
