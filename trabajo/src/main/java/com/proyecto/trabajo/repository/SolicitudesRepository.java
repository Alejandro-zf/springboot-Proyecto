package com.proyecto.trabajo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.trabajo.models.Solicitudes;

public interface SolicitudesRepository extends JpaRepository<Solicitudes, Long> {

	@Query("SELECT s FROM Solicitudes s WHERE s.fecha_fin < :now AND s.estadosolicitud = 2")
	List<Solicitudes> findVencidasNoExpiradas(@Param("now") LocalDateTime now);

}
