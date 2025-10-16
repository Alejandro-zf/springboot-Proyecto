package com.proyecto.trabajo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaDtos {
    private Byte id_cat;
    private String nom_cat;

    private List<Long> id_subcat;

}
