package com.proyecto.trabajo.repository;

import com.proyecto.trabajo.models.Estado_ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoTicketRepository extends JpaRepository<Estado_ticket, Byte> {
}
