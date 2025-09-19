package com.proyecto.trabajo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.trabajo.models.Tickets_elemento;
import com.proyecto.trabajo.models.Tickets_elementoid;

public interface Tickets_elementoRepository extends JpaRepository <Tickets_elemento,Tickets_elementoid> {
    List<Tickets_elemento> findByTickets_Id(Long ticketid);
    List<Tickets_elemento> findByElementos_Id(Long elementoid);
}
