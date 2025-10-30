package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElementoDto {
    private Long id_elemen;
    private String nom_eleme;
    private String obse;
    private String num_seri;
    private String componen;
    private Byte est_elemn;
    private String marc;

    private Long id_categ;
    private String tip_catg;

    private Long id_subcat;
    private String sub_catg;
}
