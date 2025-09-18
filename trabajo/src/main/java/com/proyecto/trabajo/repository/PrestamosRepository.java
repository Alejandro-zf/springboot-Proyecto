package com.proyecto.trabajo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Prestamos;

public interface PrestamosRepository extends JpaRepository<Prestamos, Long> {
    List<Prestamos> findByUsuario_Id(Long idUsuario);
    List<Prestamos> findByEspacio_Id(Long idEspacio);
    List<Prestamos> findByUsuarioId(Long usuarioId);
    List<Prestamos> findByEspacioId(Integer espacioId);
    List<Prestamos> findByTipo_prestContainingIgnoreCase(String tipoPrestamo);
    List<Prestamos> findByFecha_entre(LocalDateTime fechaEntrega);
    List<Prestamos> findByFecha_recep(LocalDateTime fechaRecepcion);
    List<Prestamos> findByFecha_recepIsNull();
    List<Prestamos> findByFecha_entreBeforeAndFecha_recepIsNull(LocalDateTime fecha);
    List<Prestamos> findByFecha_entreBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
