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

    private LocalDateTime fechaEntreg;
    private LocalDateTime fechaRepc;
    private String tipoPres;
    private Byte estado;

    private Long idUsuario;

    private List<Long> idsElem;

    private Long idEsp;

    
}
