package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Elemento_SolicitudesDtos {
    
    private Long id_element;
    private String nom_ele;

    private Long id_Soli;
    
}
