package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prestamos_ElementoDto {
    private String obs_pres;
    private Long id_prest;
    private String tip_prst;

    private Long id_element;
    private String nomb_ele;


    private Long prestamosId;
    private Long elementoId;
    private String obs_prest;
}
