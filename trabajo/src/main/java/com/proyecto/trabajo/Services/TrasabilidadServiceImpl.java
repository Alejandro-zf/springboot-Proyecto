package com.proyecto.trabajo.Services;

import com.proyecto.trabajo.models.Trasabilidad;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.repository.TrasabilidadRepository;
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TrasabilidadServiceImpl implements TrasabilidadService {
    @Autowired
    private TrasabilidadRepository trasabilidadRepository;
    @Autowired
    private TicketsRepository ticketsRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private ElementosRepository elementosRepository;

    @Override
    public void registrarTrasabilidad(Long ticketId, String observacion, Long usuarioId, Long elementoId) {
        Tickets ticket = ticketsRepository.findById(ticketId).orElseThrow();
        Usuarios usuario = usuariosRepository.findById(usuarioId).orElseThrow();
        Elementos elemento = elementosRepository.findById(elementoId).orElseThrow();
        Trasabilidad t = new Trasabilidad();
        t.setFecha(LocalDate.now());
        t.setObservacion(observacion);
        t.setTickets(ticket);
        t.setUsuario(usuario);
        t.setElementos(elemento);
        trasabilidadRepository.save(t);
    }
}
