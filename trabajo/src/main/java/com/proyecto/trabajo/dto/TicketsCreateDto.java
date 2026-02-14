package com.proyecto.trabajo.dto;


import java.time.LocalDateTime;
import java.util.List;
import com.proyecto.trabajo.dto.ProblemaDetalleDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketsCreateDto {

    private LocalDateTime fecha_in;
    private LocalDateTime fecha_fin;
    private String ambiente;

    // Lista de problemas con detalles únicos
    private List<ProblemaDetalleDto> problemas;

    private Long id_usu;

    private Long id_elem;

    private Long id_est_tick;
}
