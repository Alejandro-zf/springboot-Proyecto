package com.proyecto.trabajo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Trasabilidad;

import java.util.List;
public interface TrasabilidadRepository extends JpaRepository<Trasabilidad,Long>{
	List<Trasabilidad> findByTicketsId(Long ticketId);
}
