package com.proyecto.trabajo.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Tickets_elemento {

    @Embeddable
    public class Tickets_elementoid implements Serializable{

        private Long ticketid;
        private Long elementoid;
    }

    @Column(nullable = false, length = 255)
    private String  Obser_ticket;

    @Column(nullable = false, length = 25)
    private String  num_ticket;


    @EmbeddedId
    private Tickets_elementoid id = new Tickets_elementoid();
    
    @ManyToOne
    @MapsId("ticketid")
    private Tickets tickets;

    @ManyToOne
    @MapsId("elementoid")
    private Elementos elementos;
}