package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketsDtO {
    private Long id_tick;
    private LocalDateTime fech_ini;
    private LocalDateTime fech_fin;
    private String ambient;

}
