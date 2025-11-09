package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudeCreateDto {

    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_fn;
    private String ambient;
    private Integer num_fich;
    private Integer cantid;
    @JsonAlias({"id_estado_soli", "estadosoli"})
    private Integer id_estado_soli;
    private Byte estado;
    private String mensaj;

    private Long id_categoria;
    private Long id_subcategoria;

    private Long id_usu;

    private Long id_esp;

    private List<Long> ids_elem;

    public void setId_esp(String idEsp) {
        if (idEsp == null || idEsp.isBlank()) {
            this.id_esp = null;
        } else {
            this.id_esp = Long.valueOf(idEsp);
        }
    }
}
