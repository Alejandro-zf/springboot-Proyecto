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
@EqualsAndHashCode(of = {  "id_solicitud", "id_elemento" })
public class Elemento_Solicitudesid implements Serializable{
    public Elemento_Solicitudesid(Integer id, Integer id2){
    } 

    private Long id_solicitud;
    private Long id_elemento;
}
