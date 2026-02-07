package com.proyecto.trabajo.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProblemasUpdateDtos {
    
    @Size(max = 30, message = "La descripción no puede exceder 30 caracteres")
    private String descr_problem;
    
    @Size(max = 255, message = "El tipo de problema no puede exceder 255 caracteres")
    private String tipo_problema;
    
    private Long id_tick;
}
