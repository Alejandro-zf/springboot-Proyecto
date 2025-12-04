package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuariosUpdateDto {
    private Long id_Usu;
    private Long id_rl;
    private String nom_us;
    private String ape_us;
    private String corre;
    private String currentPassword; // Contraseña actual para validación
    private String password;
    private Long num_docu;
    private Byte id_td;
    private Byte est_usu;
}
