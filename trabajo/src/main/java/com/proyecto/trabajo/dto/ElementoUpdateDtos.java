package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElementoUpdateDtos {
    private Long id_elem;
    private String nom_elem;
    private Byte est;
    private String obser; // Observaciones
    private String componentes;
}
