package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Estado_solicitudesDto {
    private Long id_estad;
    private String nom_est;
    
    private Long id_soli;
}
