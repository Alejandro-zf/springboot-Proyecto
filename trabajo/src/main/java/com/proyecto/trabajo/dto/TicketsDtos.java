package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

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
    private String ambiente;
    private String Obser;
    private Byte estado;
    private String imageness; 
    
    private List<TicketProblemaDto> problemas;

    private Long id_usuario;
    private String nom_usu;

    private Long id_eleme;
    private String nom_elem;

    private Long id_est_tick;
    private String tip_est_ticket;
}
