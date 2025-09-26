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
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Accesorios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cant;
    @Column(nullable = false,length = 30)
    private String nom_acce;
    @Column(nullable = false,length = 20)
    private String marca;
    private Integer num_serie;

    @OneToMany(mappedBy = "accesorios", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Accesorios_Prestamos> acceprestamooss = new ArrayList<>();
}
