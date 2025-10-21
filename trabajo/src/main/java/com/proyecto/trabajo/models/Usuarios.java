package com.proyecto.trabajo.models;

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
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,length=50)
    private String nom_usu;
    @Column(nullable=false,length=50)
    private String ape_usu;
    @Column(nullable=false,length=100)
    private String correo;

    private Long num_doc;
    @Column(nullable=false,length=30) 
    private String password;

    @Column(nullable = false)
    private Byte estado = 1; // 0=No activo, 1=Activo

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solicitudes> solicitudes = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tickets> tickets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tip_document", nullable = false, foreignKey = @ForeignKey(name = "FK_Tip_document"))
    private Tip_documento tip_documento;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamos> prestamos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true )
    private List <Roles_Usuario> role = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trasabilidad> trasabilidad = new ArrayList<>();
}
