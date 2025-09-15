package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.ElementosMapper;
import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.repository.ElementosRepository;

@Service
public class ElementosServicesImple implements ElementosServices {
    
    @Autowired
    private ElementosRepository elementosRepo;

    @Autowired
    private ElementosMapper elementosMapper;

    @Override
    public ElementoDto getElemento(Long id_elemen) {
        Elementos elementos = elementosRepo.findById(id_elemen).get();
        return elementosMapper.toElementoDto(elementos);
    }

    @Override
    public ElementoDto saveElemento(ElementoDto elementoDto) {
        Elementos elementos = elementosMapper.toElementos(elementoDto);
        return elementosMapper.toElementoDto(elementosRepo.save(elementos));
    }

    @Override
    public List<ElementoDto> getAllElementos() {
        List<Elementos> elementos = elementosRepo.findAll();
        return elementosMapper.toElementoDtoList(elementos);
    }

    @Override
    public ElementoDto deleteElementos(Long id_elemen) {
        Elementos elementos = elementosRepo.findById(id_elemen).get();
        elementosRepo.delete(elementos);
        return elementosMapper.toElementoDto(elementos);
    }
}
