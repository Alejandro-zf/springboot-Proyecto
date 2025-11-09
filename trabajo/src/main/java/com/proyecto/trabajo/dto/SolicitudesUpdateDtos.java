package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudesUpdateDtos {
    private Long id_soli;
    private Integer id_est_soli;
    private Byte estado;

    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_fn;
    private String ambient;
    private Integer num_fich;
    private Long id_esp;
    private Long id_usu;
    private List<Long> ids_elem;
}
