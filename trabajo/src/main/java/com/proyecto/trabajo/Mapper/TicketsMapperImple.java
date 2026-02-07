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
        tickets.setAmbiente(ticketsDtos.getAmbiente());
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
        dto.setAmbiente(tickets.getAmbiente());
        dto.setImageness(tickets.getImageness());
        dto.setObser(tickets.getObservaciones());
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

        if (tickets.getProblemas() != null) {
            System.out.println("🔍 Mapeando " + tickets.getProblemas().size() + " problemas del ticket " + tickets.getId());
            dto.setProblemas(tickets.getProblemas().stream()
                .map(problema -> {
                    System.out.println("   - Problema ID: " + problema.getId() + " - " + problema.getDesc_problema());
                    return problema.getId();
                })
                .collect(Collectors.toList()));
            System.out.println("✅ DTO final tiene " + dto.getProblemas().size() + " problemas");
        } else {
            System.out.println("⚠️ El ticket " + tickets.getId() + " no tiene problemas asociados");
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
        tickets.setObservaciones(createDto.getObser());
        tickets.setImageness(createDto.getImageness());
        return tickets;
    }
}