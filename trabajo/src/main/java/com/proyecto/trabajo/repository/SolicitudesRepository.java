package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Long> {
    List<Solicitudes> findByUsuario_Id(Long idUsuario);
    List<Solicitudes> findByEstado_solicitudes_Id(Long idEstado);
}
