package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.ProblemasMapper;
import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.repository.ProblemasRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProblemasServicesImple implements ProblemasServices {

    private final ProblemasRepository problemasRepository;
    private final ProblemasMapper problemasMapper;

    public ProblemasServicesImple(
        ProblemasRepository problemasRepository,
        ProblemasMapper problemasMapper
    ) {
        this.problemasRepository = problemasRepository;
        this.problemasMapper = problemasMapper;
    }

    @Override
    public ProblemasDtos guardar(ProblemasCreateDtos dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser nulo");
        }

        Problemas problema = problemasMapper.toProblemasFromCreateDto(dto);
        problema = problemasRepository.save(problema);

        return problemasMapper.toProblemasDto(problema);
    }

    @Override
    public ProblemasDtos buscarPorId(Byte id) {
        Problemas problema = problemasRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));

        return problemasMapper.toProblemasDto(problema);
    }

    @Override
    public List<ProblemasDtos> listarTodos() {
        List<Problemas> problemas = problemasRepository.findAll();
        return problemasMapper.toProblemasDtoList(problemas);
    }

    @Override
    public void eliminar(Byte id) {
        if (!problemasRepository.existsById(id)) {
            throw new EntityNotFoundException("Problema no encontrado");
        }
        problemasRepository.deleteById(id);
    }

    @Override
    public ProblemasDtos actualizar(ProblemasDtos dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("El DTO o el ID no pueden ser nulos");
        }

        Problemas problema = problemasRepository.findById(dto.getId())
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));

        problema.setDesc_problema(dto.getDescr_problem());

        Tickets ticket = new Tickets();
        ticket.setId(dto.getId_tick());
        problema.setTicket(ticket);

        problema = problemasRepository.save(problema);

        return problemasMapper.toProblemasDto(problema);
    }
}
