package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Estado_ticket;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.Estado_TicketRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class TicketsMapperImple implements TicketsMapper {
    
    private final UsuariosRepository usuariosRepository;
    private final Estado_TicketRepository estadoTicketRepository;

    public TicketsMapperImple(UsuariosRepository usuariosRepository, Estado_TicketRepository estadoTicketRepository) {
        this.usuariosRepository = usuariosRepository;
        this.estadoTicketRepository = estadoTicketRepository;
    }
    
    @Override
    public Tickets toTickets(TicketsDtos ticketsDtos) {
        if (ticketsDtos == null)
            return null;

        Tickets tickets = new Tickets();
        tickets.setId(ticketsDtos.getId_tickets());
        tickets.setFecha_ini(ticketsDtos.getFecha_in());
        tickets.setFecha_finn(ticketsDtos.getFecha_fin());
        tickets.setAmbiente(ticketsDtos.getAmbient());

        // Mapear llaves foráneas
        if (ticketsDtos.getId_usuario() != null) {
            Usuarios usuario = usuariosRepository.findById(ticketsDtos.getId_usuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            tickets.setUsuario(usuario);
        }
        if (ticketsDtos.getId_est_tick() != null) {
            Estado_ticket estado = estadoTicketRepository.findById(ticketsDtos.getId_est_tick().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Estado de ticket no encontrado"));
            tickets.setEstado_ticket(estado);
        }

        return tickets;
    }
    
    @Override
    public TicketsDtos toTicketsDto(Tickets tickets) {
        if (tickets == null)
            return null;

        TicketsDtos ticketsDtos = new TicketsDtos();
        ticketsDtos.setId_tickets(tickets.getId());
        ticketsDtos.setFecha_in(tickets.getFecha_ini());
        ticketsDtos.setFecha_fin(tickets.getFecha_finn());
        ticketsDtos.setAmbient(tickets.getAmbiente());

        if (tickets.getUsuario() != null) {
            ticketsDtos.setId_usuario(tickets.getUsuario().getId());
            ticketsDtos.setNom_usu(tickets.getUsuario().getNom_usu());
        }
        if (tickets.getEstado_ticket() != null) {
            ticketsDtos.setId_est_tick(tickets.getEstado_ticket().getId_estado().longValue());
            ticketsDtos.setTip_est_ticket(tickets.getEstado_ticket().getNom_estado());
        }

        return ticketsDtos;
    }

    @Override
    public Tickets toTicketsFromCreateDto(TicketsCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Tickets tickets = new Tickets();
        tickets.setId(createDto.getId_tickets());
        tickets.setFecha_ini(createDto.getFecha_in());
        tickets.setFecha_finn(createDto.getFecha_fin());
        tickets.setAmbiente(createDto.getAmbient());
        return tickets;
    }
}