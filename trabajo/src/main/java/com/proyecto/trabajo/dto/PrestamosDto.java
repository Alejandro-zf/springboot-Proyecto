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
private LocalDateTime fecha_entreg;
private LocalDateTime fecha_repc;
private String tipo_pres;
private Byte estado;
private Integer cant_elem ;

private Long id_usu;
private String nom_usu;

private String id_elem;
private String nom_elem;
private String numero_serie;

private Long id_espac;
private String nom_espac;

private String nom_cat;
private String ambient;
private Long id_tecnico;
private String nombre_tecnico;

}
