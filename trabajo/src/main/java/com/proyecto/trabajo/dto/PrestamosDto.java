package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PrestamosDto {
private Long id_prest;
private LocalDateTime fecha_ent;
private LocalDateTime fech_recep;
private String tipo_pres;
}
