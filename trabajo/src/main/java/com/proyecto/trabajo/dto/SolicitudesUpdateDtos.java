package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SolicitudesUpdateDtos {
    private Long id_soli;
    @JsonAlias({"id_est_soli", "est_soli", "estado", "id_estado_soli"})
    private Integer id_est_soli;

    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_fn;
    @JsonAlias({"ambient","ambiente"})
    private String ambient;
    private Integer num_fich;
    @JsonAlias({"cantidad","cantid"})
    private Integer cantid;
    private Long id_esp;
    private Long id_usu;
    private List<Long> ids_elem;
}
