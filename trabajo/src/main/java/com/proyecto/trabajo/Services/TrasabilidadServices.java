
    package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.TrasabilidadCreateDtos;
import com.proyecto.trabajo.dto.TrasabilidadDtos;

public interface TrasabilidadServices {
    TrasabilidadDtos guardar(TrasabilidadCreateDtos dto);
    TrasabilidadDtos buscarPorId(Long id);
    List<TrasabilidadDtos> listarTodos();
    void eliminar(Long id);
    TrasabilidadDtos actualizar(TrasabilidadDtos dto);
    List<TrasabilidadDtos> buscarPorTicketId(Long ticketId);
}
