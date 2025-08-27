package com.proyecto.trabajo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String tipo_prest;
    

    @ManyToOne 
    @JoinColumn(name = "id_user")
    private Usuarios usuario;
}
