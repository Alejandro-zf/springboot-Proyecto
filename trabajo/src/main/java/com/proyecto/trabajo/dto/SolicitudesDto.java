package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

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

private Long id_elem;
private String nom_elem;

 // Nuevo: lista de IDs de elementos asociados
 private List<Long> ids_elem;

private Long acces_id;
private String nom_acces;

}
