package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.ProblemasMapper;
import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;
import com.proyecto.trabajo.models.Problemas;
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

    private void validarDescripcionProblema(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del problema es obligatoria");
        }
        
        if (descripcion.length() > 255) {
            throw new IllegalArgumentException("La descripción del problema no puede exceder 255 caracteres");
        }
    }

    @Override
    public ProblemasDtos guardar(ProblemasCreateDtos dto) {
        validarDescripcionProblema(dto.getDescr_problem());
        
        Problemas entity = problemasMapper.toProblemasFromCreateDto(dto);
        Problemas guardado = problemasRepository.save(entity);
        return problemasMapper.toProblemasDto(guardado);
    }

    @Override
    public ProblemasDtos buscarPorId(Byte id) {
        return problemasRepository.findById(id)
                .map(problemasMapper::toProblemasDto)
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado con id: " + id));
    }

    @Override
    public List<ProblemasDtos> listarTodos() {
        List<Problemas> problemas = problemasRepository.findAll();
        return problemasMapper.toProblemasDtoList(problemas);
    }

    @Override
    public void eliminar(Byte id) {
        if (!problemasRepository.existsById(id)) {
            throw new EntityNotFoundException("Problema no encontrado con id: " + id);
        }
        problemasRepository.deleteById(id);
    }

    @Override
    public ProblemasDtos actualizar(ProblemasDtos dto) {
        validarDescripcionProblema(dto.getDescr_problem());
        
        Problemas entity = problemasRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado con id: " + dto.getId()));

    entity.setDesc_problema(dto.getDescr_problem());

        Problemas actualizado = problemasRepository.save(entity);
        return problemasMapper.toProblemasDto(actualizado);
    }
}
