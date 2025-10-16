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
private LocalDateTime fecha_ini;
private LocalDateTime fecha_fn;
private String ambient;
private Integer num_fich;
private Byte est_soli;

private Long id_usu;
private String nom_usu;

private Long id_espa;
private String nom_espa;

// Opción B: concatenado en un solo String cuando hay múltiples
private String id_elem;
private String nom_elem;

// Opción B: concatenado en un solo String cuando hay múltiples
private String acces_id;
private String nom_acces;

}
