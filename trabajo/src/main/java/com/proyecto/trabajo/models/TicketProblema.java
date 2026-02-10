package com.proyecto.trabajo.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TicketProblema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Tickets ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problema_id", nullable = false)
    private Problemas problema;


    @Column(length = 255)
    private String descripcion; 

    @Column(columnDefinition = "LONGTEXT")
    private String imagenes; 
}
