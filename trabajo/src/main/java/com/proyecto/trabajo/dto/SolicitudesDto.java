package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudesDto {
	private String correo;
private Long id_soli;
private LocalDateTime fecha_ini;
private LocalDateTime fecha_fn;
private String ambient;
private Integer num_fich;
	private String est_soli;
private String mensaj;
private Integer cantid;

private Long id_usu;
private String nom_usu;

private Long id_espa;
private String nom_espa;

private String id_elem;
private String nom_elem;

private Long id_cat;
private String nom_cat;
private Long id_subcat;
private String nom_subcat;

}
