package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.repository.ProblemasRepository;

import java.util.stream.Collectors;

@Component
public class TicketsMapperImple implements TicketsMapper {

    private final ProblemasRepository problemasRepository;

    public TicketsMapperImple(ProblemasRepository problemasRepository) {
        this.problemasRepository = problemasRepository;
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
        tickets.setImageness(ticketsDtos.getImageness());

        if (ticketsDtos.getProblemas() != null) {
            tickets.setProblemas(ticketsDtos.getProblemas().stream()
                .map(id -> problemasRepository.findById(id).orElse(null))
                .collect(Collectors.toList()));
        }

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
        dto.setAmbient(tickets.getAmbiente());
        dto.setImageness(tickets.getImageness());
        dto.setObser(tickets.getObservaciones());
        mapEstadoTicket(tickets, dto);

        if (tickets.getProblemas() != null) {
            dto.setProblemas(tickets.getProblemas().stream()
                .map(problema -> problema.getId())
                .collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public Tickets toTicketsFromCreateDto(TicketsCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Tickets tickets = new Tickets();
        return tickets;
    }
}