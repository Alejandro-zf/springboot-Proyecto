package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketsCreateDto {

    private LocalDateTime fecha_in;
    private LocalDateTime fecha_fin;
    private String ambient;
    private String obser;
    
    // Campo opcional: URLs de imágenes en formato JSON (puede ser null)
    private String imageness;

    private Long id_problem;

    private Long id_usu;

    private Long id_elem;

    private Long id_est_tick;
}
