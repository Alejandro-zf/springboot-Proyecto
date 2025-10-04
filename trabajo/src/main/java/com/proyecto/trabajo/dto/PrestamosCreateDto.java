package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrestamosCreateDto {

    private LocalDateTime fecha_entreg;
    private LocalDateTime fecha_repc;
    private String tipo_pres;

    private Long id_usuario;

    private Long id_elem;

    private Long id_acces;

    private Long id_esp;

    
}
