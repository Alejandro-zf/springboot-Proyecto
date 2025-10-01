package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.ElementosMapper;
import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementosCreateDto;
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
    public ElementoDto guardar(ElementosCreateDto dto) {
        if (dto.getId_categoria() == null) {
            throw new IllegalArgumentException("id_categoria es obligatorio");
        }
        Elementos elementos = elementosMapper.toElementosFromCreateDto(dto);
        Elementos guardado = elementosRepository.save(elementos);
        return elementosMapper.toElementoDto(guardado);
    }

    @Override
    public ElementoDto buscarPorId(Long id) {
        return elementosRepository.findById(id)
                .map(elementosMapper::toElementoDto)
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
    }

    @Override
    public List<ElementoDto> listarTodos() {
        return elementosRepository.findAll()
                .stream()
                .map(elementosMapper::toElementoDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        elementosRepository.deleteById(id);
    }

    @Override
    public ElementoDto actualizarElemento(ElementoDto dto) {
        Elementos elementos = elementosMapper.toElementos(dto);
        Elementos actualizado = elementosRepository.save(elementos);
        return elementosMapper.toElementoDto(actualizado);
    }
}
