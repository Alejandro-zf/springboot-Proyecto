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
private String passwor;
}
