package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElementosCreateDto {
  
    private String nom_eleme;
    private String obse;
    private String num_seri;
    private String componen;
    private Byte est;
    private String marc;

    private Long id_categ;

    private Long id_subcat;

}
