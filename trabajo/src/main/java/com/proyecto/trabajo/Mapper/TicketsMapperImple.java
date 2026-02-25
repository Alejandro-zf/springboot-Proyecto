package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.models.Tickets;

import java.util.stream.Collectors;

@Component
public class TicketsMapperImple implements TicketsMapper {

    @Override
    public Tickets toTickets(TicketsDtos ticketsDtos) {
        if (ticketsDtos == null)
            return null;

        Tickets tickets = new Tickets();
        tickets.setId(ticketsDtos.getId_tickets());
        tickets.setFecha_ini(ticketsDtos.getFecha_in());
        tickets.setFecha_finn(ticketsDtos.getFecha_fin());
        tickets.setAmbiente(ticketsDtos.getAmbiente());
        return tickets;
    }

    @Override
    public TicketsDtos toTicketsDto(Tickets tickets) {
        if (tickets == null)
            return null;

        TicketsDtos dto = new TicketsDtos();
        dto.setId_tickets(tickets.getId());
        dto.setFecha_in(tickets.getFecha_ini());
        dto.setFecha_fin(tickets.getFecha_finn());
        dto.setAmbiente(tickets.getAmbiente());
        dto.setEstado(tickets.getEstado());

        // Mapear usuario
        if (tickets.getUsuario() != null) {
            dto.setId_usuario(tickets.getUsuario().getId());
            dto.setNom_usu(tickets.getUsuario().getNom_usu());
        }

        // Mapear elemento
        if (tickets.getElementos() != null) {
            dto.setId_eleme(tickets.getElementos().getId());
            dto.setNom_elem(tickets.getElementos().getNom_elemento());
        }

        // Mapear estado del ticket
        if (tickets.getIdEstTick() != null) {
            dto.setId_est_tick(tickets.getIdEstTick().getIdEstado().longValue());
            dto.setTip_est_ticket(tickets.getIdEstTick().getNom_estado());
        }

        if (tickets.getTicketProblemas() != null && !tickets.getTicketProblemas().isEmpty()) {
            dto.setProblemas(tickets.getTicketProblemas().stream()
                    .map(TicketProblemaMapper::toDto)
                    .collect(Collectors.toList()));

            // Extraer campos del primer problema para compatibilidad con frontend
            var primerProblema = tickets.getTicketProblemas().get(0);
            dto.setImageness(primerProblema.getImagenes());
            dto.setObser(primerProblema.getDescripcion());
            if (primerProblema.getProblema() != null) {
                dto.setNom_problm(primerProblema.getProblema().getTip_problema());
            }
        } else {
            dto.setProblemas(null);
            dto.setImageness(null);
            dto.setNom_problm(null);
            dto.setObser(null);
        }

        return dto;
    }

    @Override
    public Tickets toTicketsFromCreateDto(TicketsCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Tickets tickets = new Tickets();
        tickets.setFecha_ini(createDto.getFecha_in());
        tickets.setFecha_finn(createDto.getFecha_fin());
        tickets.setAmbiente(createDto.getAmbiente());
        return tickets;
    }
}