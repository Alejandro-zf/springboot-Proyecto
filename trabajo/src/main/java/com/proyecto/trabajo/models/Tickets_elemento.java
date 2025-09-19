package com.proyecto.trabajo.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets_elemento")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Tickets_elemento {

    @EmbeddedId
    private Tickets_elementoid id = new Tickets_elementoid();

    @Column(nullable = false, length = 255)
    private String  obser_ticket;

    @Column(nullable = false, length = 25)
    private Integer  num_ticket;
    
    @ManyToOne
    @MapsId("ticketid")
    @JoinColumn(name = "ticket_id", foreignKey =  @ForeignKey(name = "FK_ticket_id_ticket"))
    private Tickets tickets;

    @ManyToOne
    @MapsId("elementoid")
    @JoinColumn(name = "elemento_id", foreignKey =  @ForeignKey(name = "FK_elemento_id_elemento"))
    private Elementos elementos;
}