package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuariosCreateDto {
    private String nom_su;
    private String ape_su;
    private String corre;
    private Integer num_docu;
    private String pasword;
}
