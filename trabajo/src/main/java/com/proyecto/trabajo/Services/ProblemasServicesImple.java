package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.ProblemasMapper;
import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.dto.ProblemasUpdateDtos;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.repository.ProblemasRepository;
import com.proyecto.trabajo.repository.TicketsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProblemasServicesImple implements ProblemasServices {

    private final ProblemasRepository problemasRepository;
    private final ProblemasMapper problemasMapper;
    private final TicketsRepository ticketsRepository;

    public ProblemasServicesImple(
        ProblemasRepository problemasRepository,
        ProblemasMapper problemasMapper,
        TicketsRepository ticketsRepository
    ) {
        this.problemasRepository = problemasRepository;
        this.problemasMapper = problemasMapper;
        this.ticketsRepository = ticketsRepository;
    }

    private void validarDescripcionProblema(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del problema es obligatoria");
        }
        if (descripcion.length() > 30) {
            throw new IllegalArgumentException("La descripción no puede exceder 30 caracteres");
        }
    }

    private void validarTipoProblema(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de problema es obligatorio");
        }
        if (tipo.length() > 255) {
            throw new IllegalArgumentException("El tipo de problema no puede exceder 255 caracteres");
        }
    }

    @Override
    public ProblemasDtos guardar(ProblemasCreateDtos dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser nulo");
        }

        validarDescripcionProblema(dto.getDescr_problem());
        validarTipoProblema(dto.getTipo_problema());

        // Validar que el ticket existe si se proporciona
        if (dto.getId_tick() != null) {
            if (!ticketsRepository.existsById(dto.getId_tick())) {
                throw new EntityNotFoundException("Ticket no encontrado con ID: " + dto.getId_tick());
            }
        }

        Problemas problema = problemasMapper.toProblemasFromCreateDto(dto);
        problema = problemasRepository.save(problema);

        return problemasMapper.toProblemasDto(problema);
    }

    @Override
    public ProblemasDtos buscarPorId(Byte id) {
        Problemas problema = problemasRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado con ID: " + id));

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
            throw new EntityNotFoundException("Problema no encontrado con ID: " + id);
        }
        problemasRepository.deleteById(id);
    }

    @Override
    public ProblemasDtos actualizar(Byte id, ProblemasUpdateDtos dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser nulo");
        }

        Problemas problema = problemasRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado con ID: " + id));

        // Validar campos si se proporcionan
        if (dto.getDescr_problem() != null && !dto.getDescr_problem().isBlank()) {
            validarDescripcionProblema(dto.getDescr_problem());
        }

        if (dto.getTipo_problema() != null && !dto.getTipo_problema().isBlank()) {
            validarTipoProblema(dto.getTipo_problema());
        }

        // Validar que el ticket existe si se proporciona
        if (dto.getId_tick() != null) {
            if (!ticketsRepository.existsById(dto.getId_tick())) {
                throw new EntityNotFoundException("Ticket no encontrado con ID: " + dto.getId_tick());
            }
        }

        // Actualizar usando el mapper
        problemasMapper.updateProblemasFromUpdateDto(dto, problema);

        problema = problemasRepository.save(problema);

        return problemasMapper.toProblemasDto(problema);
    }
}
