
package com.proyecto.trabajo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Tickets;

public interface TicketsRepository extends JpaRepository <Tickets,Long>{

	// Tickets con estado activo (1)
	List<Tickets> findByEstado(Byte estado);
}
