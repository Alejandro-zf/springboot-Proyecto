package com.proyecto.trabajo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tickets_elementoDto {
    private String obs_ticket;
    private Integer nume_ticket;
    private Long id_Ticket;
    private Long id_element;
    private String nom_ele;
}
