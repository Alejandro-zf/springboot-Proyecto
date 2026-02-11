package com.proyecto.trabajo.dto;

import lombok.Data;

@Data
public class TicketProblemaDto {
    private Byte problemaId;
    private String descripcion;
    private String imagenes;
    private String tipoProblema;
}
