package com.proyecto.trabajo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Tickets;

public interface TicketsRepository extends JpaRepository <Tickets,Long>{
    List<Tickets> findByUsuarioId(Long usuarioId);
    List<Tickets> findByEstado_ticketId_estado(Byte estadoId);
    List<Tickets> findByAmbienteContainingIgnoreCase(String ambiente);
    List<Tickets> findByFecha_ini(LocalDateTime fechaInicio);
    List<Tickets> findByFecha_finn(LocalDateTime fechaFin);
    List<Tickets> findByFecha_iniBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
