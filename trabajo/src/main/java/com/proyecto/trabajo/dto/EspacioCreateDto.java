package com.proyecto.trabajo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EspacioCreateDto {

    @NotBlank(message = "El nombre del espacio es obligatorio")
    private String nom_espa;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    // Estado es opcional, por defecto será 1 (Activo)
    private Byte estadoespacio;

    // Imágenes en formato JSON string (array de base64)
    private String imagenes;
}
