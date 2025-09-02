package com.proyecto.trabajo.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Tip_documento {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Byte id_documento;
   
   @Column(nullable = false,length=30)
   private String tipo_doc; 
   
   @OneToMany(mappedBy = "tip_documento")
   private List<Usuarios> usuarios;
}
 