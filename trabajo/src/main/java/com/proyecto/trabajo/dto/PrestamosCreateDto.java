package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrestamosCreateDto {
    private Long id_prest;
    private LocalDateTime fecha_entreg;
    private LocalDateTime fecha_repc;
    private String tipo_pres;
}
