
package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proyecto.trabajo.models.Prestamos;

public interface PrestamosRepository extends JpaRepository <Prestamos,Long>{
	// Préstamos con estado activo (1)
	@Query("SELECT DISTINCT p FROM Prestamos p LEFT JOIN FETCH p.solicitudes s LEFT JOIN FETCH s.categoria WHERE p.estado = :estado")
	List<Prestamos> findByEstado(Byte estado);
	
	@Query("SELECT DISTINCT p FROM Prestamos p LEFT JOIN FETCH p.solicitudes s LEFT JOIN FETCH s.categoria")
	List<Prestamos> findAll();
}