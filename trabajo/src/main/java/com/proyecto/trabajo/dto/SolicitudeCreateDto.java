package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudeCreateDto {
 

    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_fn;
    private String ambient;
    private Integer num_fich;
    private Byte estadosoli;

    private Long id_usu;

    private Long id_esp;

    // Aceptar múltiples elementos en la solicitud
    private List<Long> ids_elem;

    private Long id_acces;
}
