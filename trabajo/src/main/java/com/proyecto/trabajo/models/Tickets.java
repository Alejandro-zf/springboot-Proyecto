package com.proyecto.trabajo.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_ticket;
    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_finn;
    
    @Column(nullable = false, length = 15)
    private String ambiente;
    

    @ManyToOne
    @JoinColumn(name = "id_usu", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_usu"))
    private Usuarios usuario;

    @OneToMany(mappedBy = "tickets", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Tickets_elemento> element = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "estado_ticket", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_estado"))
    private Estado_ticket estado_ticket;

}
