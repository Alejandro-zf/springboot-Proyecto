package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubcategoriaDtos {
    private Long id;
    private String nom_subcateg;

    private Long id_cat;
    private String nom_cat;
}
