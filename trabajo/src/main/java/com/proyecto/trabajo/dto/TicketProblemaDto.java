package com.proyecto.trabajo.dto;

import lombok.Data;

@Data
public class TicketProblemaDto {
    private Long id;
    private Long ticketId;
    private Byte problemaId;
    private String descripcion;
    private String imagenes;
}
