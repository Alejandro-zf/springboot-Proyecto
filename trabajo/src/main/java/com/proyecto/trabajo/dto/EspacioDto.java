package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EspacioDto {

    private Integer id;
    private String nom_espa;
    private String descripcion;
    private Byte estadoespacio;
    private String imagenes;
}
