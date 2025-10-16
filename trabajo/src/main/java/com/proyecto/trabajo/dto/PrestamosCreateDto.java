package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

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

    // Múltiples elementos asociados al préstamo
    private List<Long> ids_elem;

    // Múltiples accesorios asociados al préstamo
    private List<Long> ids_acces;

    private Long id_esp;

    
}
