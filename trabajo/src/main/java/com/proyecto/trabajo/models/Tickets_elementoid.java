package com.proyecto.trabajo.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id_ticket", "id_elemento" })

public class Tickets_elementoid implements Serializable {
    public Tickets_elementoid(Integer id, Integer id2){
    }

    private Long id_ticket;
    private Long id_elemento;

}
