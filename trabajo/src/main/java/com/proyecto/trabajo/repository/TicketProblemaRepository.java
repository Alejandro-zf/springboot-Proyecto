package com.proyecto.trabajo.repository;

import com.proyecto.trabajo.models.TicketProblema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketProblemaRepository extends JpaRepository<TicketProblema, Long> {
    List<TicketProblema> findByTicketId(Long ticketId);
    List<TicketProblema> findByProblemaId(Long problemaId);
}
