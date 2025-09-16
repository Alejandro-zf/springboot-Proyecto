package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.dto.TicketsDtos;

public interface TicketsServices {
    TicketsDtos guardar(TicketsDtos dto);
    TicketsDtos buscarPorId(Long id);
    java.util.List<TicketsDtos> listarTodos();
    void eliminar(Long id);
}