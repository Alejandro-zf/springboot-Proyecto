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
public class Prestamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_prestamo;
    private LocalDateTime fecha_entre;
    private LocalDateTime fecha_recep;

    @Column(nullable = false, length = 30)
    private String tipo_prest;
    

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_user"))
    private Usuarios usuario;

    
    @OneToMany(mappedBy = "prestamos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamos_Elemento> elemento = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_espacio", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_espaciio"))
    private Espacio espacio;
}
