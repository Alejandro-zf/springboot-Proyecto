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

private Long id_usu;
private String nom_usu;

// Concatenado cuando hay múltiples elementos, ej: "2,3,4"
private String id_elem;
private String nom_elem;

private Long id_espac;
private String nom_espac;

// Concatenado cuando hay múltiples accesorios, ej: "1,5"
private String id_acceso;
private String nom_aces;

}
