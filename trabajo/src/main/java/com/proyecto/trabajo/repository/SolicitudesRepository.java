package com.proyecto.trabajo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.trabajo.models.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Long> {

	// Recupera solicitudes cuya fecha_inicio ya pasó y que no estén ya marcadas como inactivas (3)
	@Query("SELECT s FROM Solicitudes s WHERE s.fecha_inicio < :now AND s.estadosolicitud <> 3")
	List<Solicitudes> findVencidasNoExpiradas(@Param("now") LocalDateTime now);

}
