package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaUpdateDtos {
    private String nom_categoria;
    private Byte estado; // 1=Activo, 0=Inactivo
}
