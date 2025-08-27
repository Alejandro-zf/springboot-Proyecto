package com.proyecto.trabajo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id_roles;
    @Column(nullable = false, length = 3)
    private String nom_rol;
}
