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
    private Integer cant_elem;

    private Long id_element;
    private String nomb_ele;


}
