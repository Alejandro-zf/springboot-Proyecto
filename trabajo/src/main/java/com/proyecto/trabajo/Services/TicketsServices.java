package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.TicketsDtos;

public interface TicketsServices {
    TicketsDtos guardar(TicketsDtos dto);
    TicketsDtos buscarPorId(Long id);
    List<TicketsDtos> listarTodos();
    void eliminar(Long id);
    TicketsDtos actualizarTicket(TicketsDtos dto);
}
