
package com.proyecto.trabajo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.proyecto.trabajo.models.Tickets;

public interface TicketsRepository extends JpaRepository<Tickets, Long> {
    // Tickets por id del estado dentro de la relación id_est_tick
    List<Tickets> findByIdEstTick_IdEstado(Byte idEstado);
}
