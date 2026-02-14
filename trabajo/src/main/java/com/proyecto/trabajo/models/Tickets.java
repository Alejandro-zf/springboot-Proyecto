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
    private Long id;
    private LocalDateTime fecha_ini;
    private LocalDateTime fecha_finn;
    
    @Column(nullable = false, length = 30)
    private String ambiente;


    @Column(nullable = false)
    private Byte estado = 2; // 1=activo/pendiente, 2=inactivo, 3=solucionado
    
    @ManyToOne
    @JoinColumn(name = "id_usu", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_usu"))
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_est_tick", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_estado"))
    private Estado_ticket idEstTick;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketProblema> ticketProblemas = new ArrayList<>();

    @OneToMany(mappedBy = "tickets", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trasabilidad> trasabilidad = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "elementos", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_elementos"))
    private Elementos elementos;

    public Estado_ticket getIdEstTick() {
        return idEstTick;
    }

    public void setIdEstTick(Estado_ticket idEstTick) {
        this.idEstTick = idEstTick;
    }
}
