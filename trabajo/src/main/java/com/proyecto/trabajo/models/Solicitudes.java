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
public class Solicitudes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;

    @Column(nullable = false,length = 35)
    private String ambiente;
    
    private Byte estadosolicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuari", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_usuari"))
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_espacio", nullable = true, foreignKey = @ForeignKey(name = "FK_Id_espacio"))
    private Espacio espacio;

    @OneToMany(mappedBy = "solicitudes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Elemento_Solicitudes> elemento = new ArrayList<>();

    @OneToMany(mappedBy = "solicitudes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accesorios_solicitudes> solicitudesacce = new ArrayList<>();

}
