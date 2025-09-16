package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Categoria;

@Component
public class ElementosMapperImple implements ElementosMapper {

    @Override
    public Elementos toElementos(ElementoDto elementoDto) {
        if (elementoDto == null) {
            return null;
        }
        Elementos elementos = new Elementos();
        elementos.setId(elementoDto.getId_elemen());
        elementos.setNom_elemento(elementoDto.getNom_eleme());
        elementos.setObser(elementoDto.getObse());
        elementos.setNum_serie(elementoDto.getNum_seri());
        elementos.setComponentes(elementoDto.getComponen());

        return elementos;
    }

    // Alias explícitos estilo toEntity/toDTO
    @Override
    public Elementos toEntity(ElementoDto dto) {
        return toElementos(dto);
    }

    @Override
    public ElementoDto toElementoDto(Elementos elementos) {
        if (elementos == null) {
            return null;
        }
        ElementoDto elementoDto = new ElementoDto();
        elementoDto.setId_elemen(elementos.getId());
        elementoDto.setNom_eleme(elementos.getNom_elemento());
        elementoDto.setObse(elementos.getObser());
        elementoDto.setNum_seri(elementos.getNum_serie());
        elementoDto.setComponen(elementos.getComponentes());
        
        return elementoDto;
    }

    @Override
    public ElementoDto toDTO(Elementos entity) {
        return toElementoDto(entity);
    }

    @Override
    public List<ElementoDto> toElementoDtoList(List<Elementos> elementos) {
        if (elementos == null) {
            return List.of();
        }
        List<ElementoDto> elementoDtos = new ArrayList<ElementoDto>(elementos.size());

        for (Elementos elemento : elementos) {
            elementoDtos.add(toElementoDto(elemento));
        }
        return elementoDtos;
    }
}
