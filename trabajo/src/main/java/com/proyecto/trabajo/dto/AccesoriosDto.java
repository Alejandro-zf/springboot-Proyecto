package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccesoriosDto {
    private Integer id_acces;
    private Integer can;
    private String nom_accceso;
    private String marca;
    private Integer Num_ser;
}
