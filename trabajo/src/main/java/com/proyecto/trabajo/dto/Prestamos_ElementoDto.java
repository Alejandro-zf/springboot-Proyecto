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
    private Long id_element;


}
