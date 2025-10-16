package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.TicketsMapper;
import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Estado_ticket;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.Estado_TicketRepository;
import com.proyecto.trabajo.repository.ProblemasRepository;
import com.proyecto.trabajo.repository.ElementosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TicketsServicesImple implements TicketsServices {

    private final TicketsRepository ticketsRepository;
    private final TicketsMapper ticketsMapper;
    private final UsuariosRepository usuariosRepository;
    private final Estado_TicketRepository estadoTicketRepository;
    private final ProblemasRepository problemasRepository;
    private final ElementosRepository elementosRepository;

    public TicketsServicesImple(TicketsRepository ticketsRepository, TicketsMapper ticketsMapper,
            UsuariosRepository usuariosRepository, Estado_TicketRepository estadoTicketRepository, ProblemasRepository problemasRepository, ElementosRepository elementosRepository) {
        this.ticketsRepository = ticketsRepository;
        this.ticketsMapper = ticketsMapper;
        this.usuariosRepository = usuariosRepository;
        this.estadoTicketRepository = estadoTicketRepository;
        this.problemasRepository = problemasRepository;
        this.elementosRepository = elementosRepository;
    }
    private void sincronizarEstadoElementoPorTicket(Tickets ticket) {
        if (ticket == null) return;
        Elementos elemento = ticket.getElementos();
        Estado_ticket estado = ticket.getEstado_ticket();
        if (elemento == null || estado == null || estado.getId_estado() == null) return;

        final byte nuevoEstadoElemento = (estado.getId_estado() == 1) ? (byte) 2 : (byte) 1;

        if (elemento.getEstadosoelement() == null || elemento.getEstadosoelement() != nuevoEstadoElemento) {
            elemento.setEstadosoelement(nuevoEstadoElemento);
            elementosRepository.save(elemento);
        }
    }

    @Override
    @Transactional
    public TicketsDtos guardar(TicketsCreateDto dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalStateException("id_usu es obligatorio");
        }
        // est_tick puede ser null; por defecto será 'pendiente' (ID 3)
        if (dto.getId_problem() == null) {
            throw new IllegalStateException("id_problem es obligatorio");
        }
        if (dto.getAmbient() == null || dto.getAmbient().isBlank()) {
            throw new IllegalStateException("ambient es obligatorio");
        }
        if (dto.getFecha_in() == null) {
            dto.setFecha_in(LocalDateTime.now());
        }
        if (dto.getObser() == null) {
            dto.setObser("");
        }

        Tickets tickets = ticketsMapper.toTicketsFromCreateDto(dto);

        if (tickets.getEstado_ticket() == null) {
            Estado_ticket estadoPendiente = estadoTicketRepository.findById((byte)3)
                .orElseThrow(() -> new EntityNotFoundException("Estado de ticket 'pendiente' (3) no encontrado"));
            tickets.setEstado_ticket(estadoPendiente);
        }

        if (tickets.getProblemas() == null && dto.getId_problem() != null) {
            Problemas problema = problemasRepository.findById(dto.getId_problem().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
            tickets.setProblemas(problema);
        }
        if (tickets.getObservaciones() == null) {
            tickets.setObservaciones(dto.getObser());
        }
        if (tickets.getElementos() == null && dto.getId_elem() != null) {
            Elementos elemento = elementosRepository.findById(dto.getId_elem())
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
            tickets.setElementos(elemento);
        }

        Tickets guardado = ticketsRepository.save(tickets);
        sincronizarEstadoElementoPorTicket(guardado);
        return ticketsMapper.toTicketsDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketsDtos buscarPorId(Long id) {
        Tickets tickets = ticketsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        return ticketsMapper.toTicketsDto(tickets);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarTodos() {
        return ticketsRepository.findAll()
                .stream()
                .map(ticketsMapper::toTicketsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Tickets tickets = ticketsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        ticketsRepository.delete(tickets);
    }

    @Override
    @Transactional
    public TicketsDtos actualizarTicket(TicketsDtos dto) {
        Tickets tickets = ticketsRepository.findById(dto.getId_tickets())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        
        tickets.setFecha_ini(dto.getFecha_in());
        tickets.setFecha_finn(dto.getFecha_fin());
        tickets.setAmbiente(dto.getAmbient());
        if (dto.getObser() == null) {
            tickets.setObservaciones("");
        } else {
            tickets.setObservaciones(dto.getObser());
        }

        if (dto.getId_usuario() != null) {
            Usuarios usuario = usuariosRepository.findById(dto.getId_usuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            tickets.setUsuario(usuario);
        }
        if (dto.getId_est_tick() != null) {
            Estado_ticket estado = estadoTicketRepository.findById(dto.getId_est_tick().byteValue())
                    .orElseThrow(() -> new EntityNotFoundException("Estado de ticket no encontrado"));
            tickets.setEstado_ticket(estado);
        }
        if (dto.getProbloem_id() != null) {
            Problemas problema = problemasRepository.findById(dto.getProbloem_id().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
            tickets.setProblemas(problema);
        }
        if (dto.getId_eleme() != null) {
            Elementos elemento = elementosRepository.findById(dto.getId_eleme())
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
            tickets.setElementos(elemento);
        }
        
        Tickets actualizado = ticketsRepository.save(tickets);
        sincronizarEstadoElementoPorTicket(actualizado);
        return ticketsMapper.toTicketsDto(actualizado);
    }
}

