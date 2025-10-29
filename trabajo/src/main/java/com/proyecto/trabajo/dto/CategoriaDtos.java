package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaDtos {
    private Byte id_cat;
    private String nom_cat;

    private Long id_subcat;
    private String nom_subcast;

}
