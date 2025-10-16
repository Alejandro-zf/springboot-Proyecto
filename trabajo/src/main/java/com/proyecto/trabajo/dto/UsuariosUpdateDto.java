package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuariosUpdateDto {
    private Long id_Usu;
    private String nom_us;
    private String ape_us;
    private String corre;
    private String password;
    private Byte est_usu;
}
