package com.proyecto.trabajo.Services;

import java.util.List;
import com.proyecto.trabajo.dto.Tickets_elementoDto;

public interface Tickets_elementoService {
    Tickets_elementoDto asignar(Tickets_elementoDto dto);
    List<Tickets_elementoDto> listarPorTicket(Long ticketId);
    List<Tickets_elementoDto> listarPorElemento(Long elementoId);
    void eliminarAsignacion(Long ticketId, Long elementoId);
    List<Tickets_elementoDto> asignarElementos(List<Tickets_elementoDto> dtos);
}
