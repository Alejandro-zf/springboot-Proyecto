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

    private Long id_usu;

    private Long id_elem;

    private Long est_tick;
}
