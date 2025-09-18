package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.models.Estado_ticket;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.Estado_TicketRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class TicketsMapperImple implements TicketsMapper {

    private final UsuariosRepository usuariosRepository;
    private final Estado_TicketRepository estadoTicketRepository;

    public TicketsMapperImple(UsuariosRepository usuariosRepository, 
                             Estado_TicketRepository estadoTicketRepository) {
        this.usuariosRepository = usuariosRepository;
        this.estadoTicketRepository = estadoTicketRepository;
    }

    @Override
    public Tickets toTickets(TicketsDtos ticketsDto) {
        if (ticketsDto == null) {
            return null;
        }
        
        Tickets tickets = new Tickets();
        tickets.setId(ticketsDto.getId_tickets());
        tickets.setFecha_ini(ticketsDto.getFecha_in());
        tickets.setFecha_finn(ticketsDto.getFecha_fin());
        tickets.setAmbiente(ticketsDto.getAmbient());

        // Usuario obligatorio
        if (ticketsDto.getId_usuario() != null) {
            Usuarios usuario = usuariosRepository.findById(ticketsDto.getId_usuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            tickets.setUsuario(usuario);
        }

        // Estado ticket obligatorio
        if (ticketsDto.getId_estado_ticket() != null) {
            Estado_ticket estadoTicket = estadoTicketRepository.findById(ticketsDto.getId_estado_ticket())
                    .orElseThrow(() -> new EntityNotFoundException("Estado de ticket no encontrado"));
            tickets.setEstado_ticket(estadoTicket);
        }
        
        return tickets;
    }

    @Override
    public TicketsDtos toTicketsDto(Tickets tickets) {
        if (tickets == null) {
            return null;
        }
        
        TicketsDtos ticketsDto = new TicketsDtos();
        ticketsDto.setId_tickets(tickets.getId());
        ticketsDto.setFecha_in(tickets.getFecha_ini());
        ticketsDto.setFecha_fin(tickets.getFecha_finn());
        ticketsDto.setAmbient(tickets.getAmbiente());
        
        // Relaciones
        ticketsDto.setId_usuario(tickets.getUsuario() != null ? tickets.getUsuario().getId() : null);
        ticketsDto.setId_estado_ticket(tickets.getEstado_ticket() != null ? tickets.getEstado_ticket().getId_estado() : null);
        
        return ticketsDto;
    }
}