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
    private Byte id_estado;
    
    @Column (nullable = false, length =15)
    private String nom_estado;

    @OneToMany(mappedBy = "id_est_tick", cascade = CascadeType.ALL,orphanRemoval = true)
    private List <Tickets> tickets = new ArrayList<>();
}   
