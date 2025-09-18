package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Prestamos;

public interface PrestamosRepository extends JpaRepository<Prestamos, Long> {
    List<Prestamos> findByUsuario_Id(Long idUsuario);
    List<Prestamos> findByEspacio_Id(Long idEspacio);
}
