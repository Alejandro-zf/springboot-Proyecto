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
@EqualsAndHashCode(of = { "ticketid", "elementoid" })

public class Tickets_elementoid implements Serializable {
    public Tickets_elementoid(Integer id_ticket, Integer id_elemento){
    }

    private Long ticketid;
    private Long elementoid;
}
