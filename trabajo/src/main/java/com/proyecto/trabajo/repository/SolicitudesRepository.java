package com.proyecto.trabajo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Long> {
    List<Solicitudes> findByUsuario_Id(Long idUsuario);
    List<Solicitudes> findByEstado_solicitudes_Id(Long idEstado);
    List<Solicitudes> findByEspacioId(Integer espacioId);
    List<Solicitudes> findByEstado_solicitudesId_estado_soli(Integer estadoId);
    List<Solicitudes> findByFecha_inicio(LocalDateTime fechaInicio);
    List<Solicitudes> findByFecha_fin(LocalDateTime fechaFin);
    List<Solicitudes> findByAmbienteContainingIgnoreCase(String ambiente);
    List<Solicitudes> findByFecha_inicioBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
