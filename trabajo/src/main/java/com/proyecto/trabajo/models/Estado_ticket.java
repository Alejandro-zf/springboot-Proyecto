package com.proyecto.trabajo.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Estado_ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte idEstado;

    @Column (nullable = false, length =15)
    private String nom_estado;

    @OneToMany(mappedBy = "idEstTick", cascade = CascadeType.ALL,orphanRemoval = true)
    private List <Tickets> tickets = new ArrayList<>();

    public Byte getIdEstado() {
        return idEstado;
    }

    // setIdEstado ya está definido arriba, eliminamos duplicado

    public String getNom_estado() {
        return nom_estado;
    }

    public void setNom_estado(String nom_estado) {
        this.nom_estado = nom_estado;
    }

    public List<Tickets> getTickets() {
        return tickets;
    }

    public void setTickets(List<Tickets> tickets) {
        this.tickets = tickets;
    }

    public void setIdEstado(Byte idEstado) {
        this.idEstado = idEstado;
    }
}   
