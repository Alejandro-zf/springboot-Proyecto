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
    
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;

    private Integer cantidad;

    @Column(nullable = false,length = 35)
    private String ambiente;

    private Integer num_ficha;    

    @Column(nullable = true,length=255)
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "id_usuari", nullable = false, foreignKey = @ForeignKey(name = "FK_Id_usuari"))
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id_espacio", nullable = true, foreignKey = @ForeignKey(name = "FK_Id_espacio"))
    private Espacio espacio;

    @OneToMany(mappedBy = "solicitudes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Elemento_Solicitudes> elemento = new ArrayList<>();

    @OneToMany(mappedBy = "solicitudes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List <Prestamos> prestamos = new ArrayList<>();

    @Column(name = "estadosolicitud", nullable = false)
    private Byte estadosolicitud = 2; // 0=No aprobado, 1=Aprobado, 2=Pendiente, 3=Denegado, 4=Inactiva/Expirada

    @ManyToOne
    @JoinColumn(name = "id_estado_solicitud", nullable = true, foreignKey = @ForeignKey(name = "FK_Id_estado_solicitud"))
    private Estado_solicitudes estado_solicitudes;

    @ManyToOne
    @JoinColumn(name = "id_subcategoria", nullable = true, foreignKey = @ForeignKey(name = "FK_Solicitud_Subcategoria"))
    private Sub_categoria sub_categoria;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = true, foreignKey = @ForeignKey(name = "FK_Id_categoria"))
    private Categoria categoria;
    
}