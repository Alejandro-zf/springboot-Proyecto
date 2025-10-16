package com.proyecto.trabajo.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudeCreateDto {
 

    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_fn;
    private String ambient;
    private Integer num_fich;
    private Integer id_estado_soli;

    private Long id_usu;

    private Long id_esp;

    // Aceptar múltiples elementos en la solicitud
    private List<Long> ids_elem;

    // Aceptar múltiples accesorios en la solicitud
    @JsonAlias({"id_acces"})
    private List<Long> ids_acces;

    // Permitir que id_esp llegue como "" y se convierta a null
    public void setId_esp(String idEsp) {
        if (idEsp == null || idEsp.isBlank()) {
            this.id_esp = null;
        } else {
            this.id_esp = Long.valueOf(idEsp);
        }
    }
}
