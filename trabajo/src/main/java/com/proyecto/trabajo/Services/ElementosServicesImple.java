package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.ElementosMapper;
import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.repository.ElementosRepository;

@Service
public class ElementosServicesImple implements ElementosServices {
    
    private final ElementosRepository elementosRepo;
    private final ElementosMapper elementosMapper;

    public ElementosServicesImple(ElementosRepository elementosRepo, ElementosMapper elementosMapper) {
        this.elementosRepo = elementosRepo;
        this.elementosMapper = elementosMapper;
    }

    @Override
    public ElementoDto guardar(ElementoDto dto) {
        Elementos elementos = elementosMapper.toElementos(dto);
        return elementosMapper.toElementoDto(elementosRepo.save(elementos));
    }

    @Override
    public ElementoDto buscarPorId(Long id) {
        Elementos elementos = elementosRepo.findById(id).orElse(null);
        return elementosMapper.toElementoDto(elementos);
    }

    @Override
    public List<ElementoDto> listarTodos() {
        List<Elementos> elementos = elementosRepo.findAll();
        return elementosMapper.toElementoDtoList(elementos);
    }

    @Override
    public void eliminar(Long id) {
        elementosRepo.deleteById(id);
    }
}
