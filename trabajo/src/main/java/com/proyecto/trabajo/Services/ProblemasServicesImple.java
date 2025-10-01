package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.ProblemasMapper;
import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.repository.ProblemasRepository;
import com.proyecto.trabajo.repository.TicketsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProblemasServicesImple implements ProblemasServices {

    private final ProblemasRepository problemasRepository;
    private final TicketsRepository ticketsRepository;
    private final ProblemasMapper problemasMapper;

    public ProblemasServicesImple(
        ProblemasRepository problemasRepository,
        TicketsRepository ticketsRepository,
        ProblemasMapper problemasMapper
    ) {
        this.problemasRepository = problemasRepository;
        this.ticketsRepository = ticketsRepository;
        this.problemasMapper = problemasMapper;
    }

    @Override
    @Transactional
    public ProblemasDtos guardar(ProblemasCreateDtos dto) {
        if (dto.getDescr_problem() == null || dto.getDescr_problem().isBlank()) {
            throw new IllegalArgumentException("descr_problem es obligatorio");
        }
        Problemas entity = problemasMapper.toProblemasFromCreateDto(dto);
        Problemas guardado = problemasRepository.save(entity);
        // Si viene id_tick, ya se asignó en el mapper el problema al ticket
        if (dto.getId_tick() != null) {
            Tickets ticket = ticketsRepository.findById(dto.getId_tick())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            ticket.setProblemas(guardado);
            ticketsRepository.save(ticket);
        }
        return problemasMapper.toProblemasDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ProblemasDtos buscarPorId(Byte id) {
        Problemas entity = problemasRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
        return problemasMapper.toProblemasDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProblemasDtos> listarTodos() {
        return problemasRepository.findAll()
            .stream()
            .map(problemasMapper::toProblemasDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Byte id) {
        Problemas entity = problemasRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
        problemasRepository.delete(entity);
    }

    @Override
    @Transactional
    public ProblemasDtos actualizar(ProblemasDtos dto) {
        Problemas entity = problemasRepository.findById(dto.getId())
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));

        // Actualizar descripción con reflexión (no hay setters)
        try {
            if (dto.getDescr_problem() != null) {
                java.lang.reflect.Field fDesc = Problemas.class.getDeclaredField("desc_problema");
                fDesc.setAccessible(true);
                fDesc.set(entity, dto.getDescr_problem());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error actualizando Problemas", e);
        }

        // Actualizar relación con ticket si viene
        if (dto.getId_tick() != null) {
            Tickets ticket = ticketsRepository.findById(dto.getId_tick())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            ticket.setProblemas(entity);
            ticketsRepository.save(ticket);
        }

        Problemas actualizado = problemasRepository.save(entity);
        return problemasMapper.toProblemasDto(actualizado);
    }
}
