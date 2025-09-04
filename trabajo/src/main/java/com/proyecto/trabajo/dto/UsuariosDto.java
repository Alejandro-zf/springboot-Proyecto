package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuariosDto {
    private Long id_usua;
    private String nomb_usu;
    private String apel_usu;
    private String coorreo;
    private Integer num_docu;
    private String passwor;


}
