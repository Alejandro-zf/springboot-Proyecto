package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Espacio;

public interface EspacioRepository extends JpaRepository <Espacio,Integer> {
    List<Espacio> findByNom_espaContainingIgnoreCase(String nombre);
}
