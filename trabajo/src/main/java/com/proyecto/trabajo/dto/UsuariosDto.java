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


private Integer id_estad_us;
private String nom_est;

private Long id_soli;
private String esta;

private Long id_ticke;

private Byte id_tip_docu;
private String tip_docu;

private Long id_prest;
private String tip_prst;

private Long id_rol;
private String nomb_rol;
}
