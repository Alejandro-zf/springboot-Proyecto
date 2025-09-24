package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuariosDto {
private Long id_usuari;
private String nom_usua;
private String ape_usua;
private String corre;
private Integer num_docu;
private Byte nom_est;

private Byte id_tip_docu;
private String tip_docu;

private Long id_rol;
private String nomb_rol;
}
