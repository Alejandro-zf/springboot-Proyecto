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
    private Long num_doc;
    private Long id_usu_reporta;
    private String nom_us_reporta;
    private Long num_doc_reporta;

    private Long id_ticet;
    private String nom_problm;

    private Long id_elemen;
    private String nom_elemen;
}
