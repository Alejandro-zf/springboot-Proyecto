package com.proyecto.trabajo.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProblemaDetalleDto {
    private Long id;
    private String descripcion;
    private List<String> imagenes;
}
