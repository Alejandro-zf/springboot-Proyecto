package com.proyecto.trabajo.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id_prestamo","id_elemento"})
public class Prestamos_Elementoid implements Serializable{
    public Prestamos_Elementoid (Integer id, Integer id2){
    }
    private Long id_prestamo;
    private Long id_elemento;
}
