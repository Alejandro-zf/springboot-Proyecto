package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudeCreateDto {
 
    private Integer cant;
    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_fn;
    private String ambient;
    private Byte estadosoli;

    private Long id_usu;

    private Long id_esp;

    private Long id_elem;

    private Long id_acces;
}
