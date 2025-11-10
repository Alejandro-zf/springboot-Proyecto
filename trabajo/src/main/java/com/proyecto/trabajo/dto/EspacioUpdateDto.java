package com.proyecto.trabajo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EspacioUpdateDto {

    private String nom_espa;

    private String descripcion;

    private Byte estadoespacio;

    private String imagenes;
}
