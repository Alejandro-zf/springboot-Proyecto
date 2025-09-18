package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.ElementosMapper;
import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.repository.ElementosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ElementosServicesImple implements ElementosServices {

    private final ElementosRepository elementosRepository;
    private final ElementosMapper elementosMapper;

    public ElementosServicesImple(ElementosRepository elementosRepository, ElementosMapper elementosMapper) {
        this.elementosRepository = elementosRepository;
        this.elementosMapper = elementosMapper;
    }

    @Override
    public ElementoDto guardar(ElementoDto dto) {
        Elementos entity = elementosMapper.toEntity(dto);
        Elementos guardado = elementosRepository.save(entity);
        return elementosMapper.toDTO(guardado);
    }

    @Override
    public ElementoDto buscarPorId(Long id) {
        return elementosRepository.findById(id)
                .map(elementosMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
    }

    @Override
    public List<ElementoDto> listarTodos() {
        return elementosRepository.findAll()
                .stream()
                .map(elementosMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        elementosRepository.deleteById(id);
    }
}