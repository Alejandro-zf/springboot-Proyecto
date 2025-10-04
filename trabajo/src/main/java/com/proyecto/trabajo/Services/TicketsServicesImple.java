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
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.Estado_TicketRepository;
import com.proyecto.trabajo.repository.ProblemasRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TicketsServicesImple implements TicketsServices {

    private final TicketsRepository ticketsRepository;
    private final TicketsMapper ticketsMapper;
    private final UsuariosRepository usuariosRepository;
    private final Estado_TicketRepository estadoTicketRepository;
    private final ProblemasRepository problemasRepository;

    public TicketsServicesImple(TicketsRepository ticketsRepository, TicketsMapper ticketsMapper,
            UsuariosRepository usuariosRepository, Estado_TicketRepository estadoTicketRepository, ProblemasRepository problemasRepository) {
        this.ticketsRepository = ticketsRepository;
        this.ticketsMapper = ticketsMapper;
        this.usuariosRepository = usuariosRepository;
        this.estadoTicketRepository = estadoTicketRepository;
        this.problemasRepository = problemasRepository;
    }

    @Override
    @Transactional
    public TicketsDtos guardar(TicketsCreateDto dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalStateException("id_usu es obligatorio");
        }
        if (dto.getEst_tick() == null) {
            throw new IllegalStateException("est_tick es obligatorio");
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

        Tickets tickets = ticketsMapper.toTicketsFromCreateDto(dto);

        // Asegurar que problemas y observaciones no queden null aunque el mapper no los setee
        if (tickets.getProblemas() == null && dto.getId_problem() != null) {
            Problemas problema = problemasRepository.findById(dto.getId_problem().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
            tickets.setProblemas(problema);
        }
        if (tickets.getObservaciones() == null) {
            tickets.setObservaciones(dto.getObser());
        }

        Tickets guardado = ticketsRepository.save(tickets);
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
        // Asegurar que observaciones no quede null en actualización
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
        // Actualizar problema si viene en el DTO
        if (dto.getProbloem_id() != null) {
            Problemas problema = problemasRepository.findById(dto.getProbloem_id().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
            tickets.setProblemas(problema);
        }
        
        Tickets actualizado = ticketsRepository.save(tickets);
        return ticketsMapper.toTicketsDto(actualizado);
    }
}
