package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudesDto {
private Long id_soli;
private Integer cant;
private LocalDateTime fecha_ini;
private LocalDateTime fecha_fn;
private String ambient;


}
