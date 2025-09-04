package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EspacioDto {
    private Integer espaci_id;
    private String nom_esp;
    private LocalDateTime tiemp_uso;
    private LocalDateTime hor_soli;
    private Integer num_fich;

}
