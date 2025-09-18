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
private Byte id_estado_ticket;
}
