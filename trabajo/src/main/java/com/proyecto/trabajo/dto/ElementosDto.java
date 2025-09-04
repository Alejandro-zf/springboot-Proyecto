package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElementosDto {
    private Long id_ele;
    private String nom_ele;
    private String obs;
    private Integer num_ser;
    private String component;
}
