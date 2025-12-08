package com.proyecto.trabajo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrasabilidadCreateDtos {

    private LocalDate fech;
    private String obse;
    private Long id_usu;

    private Long id_ticet;

    private Long id_elemen;

}
