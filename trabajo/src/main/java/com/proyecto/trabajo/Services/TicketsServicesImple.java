package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.TicketsMapper;
import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Estado_ticket;
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.Estado_TicketRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TicketsServicesImple implements TicketsServices {

    private final TicketsRepository ticketsRepository;
    private final TicketsMapper ticketsMapper;
    private final UsuariosRepository usuariosRepository;
    private final Estado_TicketRepository estadoTicketRepository;

    public TicketsServicesImple(TicketsRepository ticketsRepository, TicketsMapper ticketsMapper,
            UsuariosRepository usuariosRepository, Estado_TicketRepository estadoTicketRepository) {
        this.ticketsRepository = ticketsRepository;
        this.ticketsMapper = ticketsMapper;
        this.usuariosRepository = usuariosRepository;
        this.estadoTicketRepository = estadoTicketRepository;
    }

    @Override
    @Transactional
    public TicketsDtos guardar(TicketsCreateDto dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalArgumentException("id_usu es obligatorio");
        }
        if (dto.getEst_tick() == null) {
            throw new IllegalArgumentException("est_tick es obligatorio");
        }
        Tickets tickets = ticketsMapper.toTicketsFromCreateDto(dto);
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
        
        Tickets actualizado = ticketsRepository.save(tickets);
        return ticketsMapper.toTicketsDto(actualizado);
    }
}
