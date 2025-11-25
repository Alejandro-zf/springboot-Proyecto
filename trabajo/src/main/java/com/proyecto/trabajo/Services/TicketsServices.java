package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsCreateDto;

public interface TicketsServices {
    TicketsDtos guardar(TicketsCreateDto dto);
    TicketsDtos buscarPorId(Long id);
    List<TicketsDtos> listarTodos();
    List<TicketsDtos> listarActivos();
    List<TicketsDtos> listarPendientes();
    void eliminar(Long id);
    TicketsDtos actualizar(Long id, com.proyecto.trabajo.dto.TicketsUpdateDtos dto);

    // Buscar Estado_ticket por nombre (ej: "ACTIVO")
    com.proyecto.trabajo.models.Estado_ticket getEstadoTicketByNombre(String nombre);
}
