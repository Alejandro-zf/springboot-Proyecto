package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketsDtos {
    private Long id_tickets;
    private LocalDateTime fecha_in;
    private LocalDateTime fecha_fin;
    private String ambient;

    private Long id_usuario;
    private String nom_usu;

    private Long id_eleme;
    private String nom_elem;

    private Long id_est_tick;
    private String tip_est_ticket;
}
