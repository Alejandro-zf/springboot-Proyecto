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
public class Estado_usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_est_usu;
    @Column(nullable=false,length=15)
    private String nombre_estado;

    @OneToMany(mappedBy = "estado_usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Usuarios> usuario = new ArrayList<>();    

}
