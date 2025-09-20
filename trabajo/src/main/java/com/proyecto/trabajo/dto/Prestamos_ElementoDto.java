package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prestamos_ElementoDto {
    private Long prestamosId;
    private Long elementoId;
    private String obs_prest;
}
