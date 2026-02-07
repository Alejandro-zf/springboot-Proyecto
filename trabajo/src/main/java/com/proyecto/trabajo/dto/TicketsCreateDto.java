package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketsCreateDto {

    private LocalDateTime fecha_in;
    private LocalDateTime fecha_fin;
    private String ambiente;
    private String obser;
    
    // Campo opcional: URLs de imágenes en formato JSON (puede ser null)
    private String imageness;

    private List<Long> id_problems; // Lista de problemas para un ticket

    private Long id_usu;

    private Long id_elem;

    private Long id_est_tick;
}
