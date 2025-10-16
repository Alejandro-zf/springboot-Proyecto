package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudesUpdateDtos {
    private Long id_soli;
    private Integer id_est_soli;

    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_fn;
}
