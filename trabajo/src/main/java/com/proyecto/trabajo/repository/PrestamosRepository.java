
package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Prestamos;

public interface PrestamosRepository extends JpaRepository <Prestamos,Long>{
	// Préstamos con estado activo (1)
	List<Prestamos> findByEstado(Byte estado);
}
