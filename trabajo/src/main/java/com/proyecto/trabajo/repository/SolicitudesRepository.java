package com.proyecto.trabajo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.trabajo.models.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Long> {

    @Query("SELECT s FROM Solicitudes s WHERE s.fecha_fin < :now AND s.estado_solicitudes.id = 2")
    List<Solicitudes> findVencidasNoExpiradas(@Param("now") LocalDateTime now);
    
    // 💡 NUEVA CONSULTA: Carga forzada de relaciones
    @Query("SELECT s FROM Solicitudes s " +
           "LEFT JOIN FETCH s.usuario u " + 
           "LEFT JOIN FETCH s.sub_categoria sc " + // <--- Carga la subcategoría
           "LEFT JOIN FETCH s.estado_solicitudes es " +
           "ORDER BY s.id DESC")
    List<Solicitudes> findAllWithDetails();
}