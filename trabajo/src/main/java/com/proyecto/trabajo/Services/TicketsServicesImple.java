package com.proyecto.trabajo.Services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.TicketsMapper;
import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.Estado_ticket;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Trasabilidad;
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.ProblemasRepository;
import com.proyecto.trabajo.repository.EstadoTicketRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.TrasabilidadRepository;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketsServicesImple implements TicketsServices {

    private final TicketsRepository ticketsRepository;
    private final ProblemasRepository problemasRepository;
    private final TicketsMapper ticketsMapper;
    private final EstadoTicketRepository estadoTicketRepository;
    private final ElementosRepository elementosRepository;
    private final TrasabilidadRepository trasabilidadRepository;

    public TicketsServicesImple(TicketsRepository ticketsRepository, ProblemasRepository problemasRepository, TicketsMapper ticketsMapper, EstadoTicketRepository estadoTicketRepository, ElementosRepository elementosRepository, TrasabilidadRepository trasabilidadRepository) {
        this.ticketsRepository = ticketsRepository;
        this.problemasRepository = problemasRepository;
        this.ticketsMapper = ticketsMapper;
        this.estadoTicketRepository = estadoTicketRepository;
        this.elementosRepository = elementosRepository;
        this.trasabilidadRepository = trasabilidadRepository;
    }

    @Override
    @Transactional
    public TicketsDtos guardar(TicketsCreateDto dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalStateException("id_usu es obligatorio");
        }
        
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
        // El campo imageness es opcional, si es null se mantiene así
        if (dto.getImageness() == null) {
            dto.setImageness(null);
        }

        Tickets tickets = ticketsMapper.toTicketsFromCreateDto(dto);
        // Asegura que siempre se asigne un estado
        if (tickets.getIdEstTick() == null) {
            Estado_ticket estadoPendiente = estadoTicketRepository.findById((byte)2)
                .orElseThrow(() -> new EntityNotFoundException("Estado de ticket 'pendiente' (2) no encontrado"));
            tickets.setIdEstTick(estadoPendiente);
        }

        if (dto.getId_est_tick() != null) {
            Estado_ticket estado = estadoTicketRepository.findById(dto.getId_est_tick().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Estado de ticket no encontrado"));
            tickets.setIdEstTick(estado);
        } else if (tickets.getIdEstTick() == null) {
            Estado_ticket estadoPendiente = estadoTicketRepository.findById((byte)2)
                .orElseThrow(() -> new EntityNotFoundException("Estado de ticket 'pendiente' (2) no encontrado"));
            tickets.setIdEstTick(estadoPendiente);
        }

        if (dto.getId_problem() != null) {
            Problemas problema = problemasRepository.findById(dto.getId_problem().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
            problema.setTicket(tickets);
            tickets.getProblemas().add(problema);
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
        try {
            Trasabilidad tr = new Trasabilidad();
            tr.setFecha(LocalDate.now());
            tr.setObservacion(null);
            tr.setUsuario(guardado.getUsuario());
            tr.setTickets(guardado);
            tr.setElementos(guardado.getElementos());
            trasabilidadRepository.save(tr);
        } catch (Exception ex) {
            // Log exception if necessary
        }

        sincronizarEstadoElementoPorTicket(guardado);
        return ticketsMapper.toTicketsDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketsDtos buscarPorId(Long id) {
        Tickets ticket = ticketsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        return ticketsMapper.toTicketsDto(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarTodos() {
        return ticketsRepository.findAll().stream()
            .map(ticketsMapper::toTicketsDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarActivos() {
        // Estado ACTIVO = 1 (idEstTick -> idEstado)
        return ticketsRepository.findByIdEstTick_IdEstado((byte)1)
                .stream()
                .map(ticketsMapper::toTicketsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarPendientes() {
        // Estado PENDIENTE = 2 (idEstTick -> idEstado)
        return ticketsRepository.findByIdEstTick_IdEstado((byte)2)
                .stream()
                .map(ticketsMapper::toTicketsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarFinalizados() {
        // Estados TERMINADO = 3 e INACTIVO = 4
        List<Tickets> terminados = ticketsRepository.findByIdEstTick_IdEstado((byte)3);
        List<Tickets> inactivos = ticketsRepository.findByIdEstTick_IdEstado((byte)4);
        
        List<Tickets> todos = new ArrayList<>();
        todos.addAll(terminados);
        todos.addAll(inactivos);
        
        return todos.stream()
                .map(ticketsMapper::toTicketsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!ticketsRepository.existsById(id)) {
            throw new EntityNotFoundException("Ticket no encontrado");
        }
        ticketsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TicketsDtos actualizar(Long id, com.proyecto.trabajo.dto.TicketsUpdateDtos dto) {
        Tickets tickets = ticketsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));

        // Solo actualiza los campos presentes en el DTO de actualización
        if (dto.getFecha_in() != null) {
            tickets.setFecha_ini(dto.getFecha_in());
        }
        if (dto.getFecha_fin() != null) {
            tickets.setFecha_finn(dto.getFecha_fin());
        }
        if (dto.getImageness() != null) {
            tickets.setImageness(dto.getImageness());
        }
    if (dto.getId_est_tick() != null) {
        Estado_ticket estado = estadoTicketRepository.findById(dto.getId_est_tick().byteValue())
            .orElseThrow(() -> new EntityNotFoundException("Estado de ticket no encontrado"));
        tickets.setIdEstTick(estado);
    }
    if (dto.getId_problem() != null) {
        Problemas problema = problemasRepository.findById(dto.getId_problem().byteValue())
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
        if (!tickets.getProblemas().contains(problema)) {
            tickets.getProblemas().add(problema);
        }
    }

        Tickets actualizado = ticketsRepository.save(tickets);
        sincronizarEstadoElementoPorTicket(actualizado);
        return ticketsMapper.toTicketsDto(actualizado);
    }
    @Override
    public com.proyecto.trabajo.models.Estado_ticket getEstadoTicketByNombre(String nombre) {
        if (nombre == null) return null;
        return estadoTicketRepository.findAll().stream()
            .filter(e -> e.getNom_estado() != null && e.getNom_estado().equalsIgnoreCase(nombre))
            .findFirst().orElse(null);
    }

    private void sincronizarEstadoElementoPorTicket(Tickets ticket) {
        // Implementación de la lógica para sincronizar el estado del elemento.
        // Por ejemplo, si el ticket se finaliza, el elemento podría pasar a estado "disponible".
    }
}

