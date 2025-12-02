package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.models.Trasabilidad;

public interface TrasabilidadService {
    void registrarTrasabilidad(Long ticketId, String observacion, Long usuarioId, Long elementoId);
}
