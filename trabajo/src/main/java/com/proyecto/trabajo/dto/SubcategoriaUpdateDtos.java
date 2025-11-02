package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubcategoriaUpdateDtos {
    private String nom_subcategoria;
    private Byte estado;
    private Byte id_categoria;  
}
