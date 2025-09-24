package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElementosCreateDto {
    private Long id_elemen;
    private String nom_eleme;
    private String obse;
    private Integer num_seri;
    private String componen;

}
