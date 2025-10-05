package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketsUpdateDtos {
    private Long id_ticke;
    private LocalDateTime fecha_in;
    private LocalDateTime fecha_fin;

    private Long id_est_tick;
    
}
