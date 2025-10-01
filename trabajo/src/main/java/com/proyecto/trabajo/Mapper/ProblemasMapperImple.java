package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.repository.TicketsRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ProblemasMapperImple implements ProblemasMapper {

    private final TicketsRepository ticketsRepository;

    public ProblemasMapperImple(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    @Override
    public Problemas toProblemas(ProblemasDtos dto) {
        if (dto == null) return null;
        Problemas entity = new Problemas();
        try {
            java.lang.reflect.Field fId = Problemas.class.getDeclaredField("id");
            fId.setAccessible(true);
            fId.set(entity, dto.getId());
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fDesc = Problemas.class.getDeclaredField("desc_problema");
            fDesc.setAccessible(true);
            fDesc.set(entity, dto.getDescr_problem());
        } catch (Exception ignored) {}

        if (dto.getId_tick() != null) {
            Tickets ticket = ticketsRepository.findById(dto.getId_tick())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            ticket.setProblemas(entity);
        }
        return entity;
    }

    @Override
    public ProblemasDtos toProblemasDto(Problemas entity) {
        if (entity == null) return null;
        ProblemasDtos dto = new ProblemasDtos();
        try {
            java.lang.reflect.Field fId = Problemas.class.getDeclaredField("id");
            fId.setAccessible(true);
            dto.setId((Byte) fId.get(entity));
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fDesc = Problemas.class.getDeclaredField("desc_problema");
            fDesc.setAccessible(true);
            dto.setDescr_problem((String) fDesc.get(entity));
        } catch (Exception ignored) {}
        // Derivar id_tick desde la primera relación si existe
        try {
            java.util.List<Tickets> tickets = entity.getTickets();
            if (tickets != null && !tickets.isEmpty()) {
                dto.setId_tick(tickets.get(0).getId());
            }
        } catch (Exception ignored) {}
        return dto;
    }

    @Override
    public Problemas toProblemasFromCreateDto(ProblemasCreateDtos createDto) {
        if (createDto == null) return null;
        Problemas entity = new Problemas();
        try {
            java.lang.reflect.Field fDesc = Problemas.class.getDeclaredField("desc_problema");
            fDesc.setAccessible(true);
            fDesc.set(entity, createDto.getDescr_problem());
        } catch (Exception ignored) {}
        if (createDto.getId_tick() != null) {
            Tickets ticket = ticketsRepository.findById(createDto.getId_tick())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            ticket.setProblemas(entity);
        }
        return entity;
    }
}
