package com.proyecto.trabajo.models;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Problemas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(nullable = false, length = 255)
    private String desc_problema;

    @Column(nullable = false, length = 255)
    private String Tip_problema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", foreignKey = @ForeignKey(name = "FK_Ticket_Problemas"))
    private Tickets ticket;
}
