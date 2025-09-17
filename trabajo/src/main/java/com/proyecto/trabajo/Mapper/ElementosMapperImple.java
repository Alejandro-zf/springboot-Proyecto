package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.models.Elementos;


@Component
public class ElementosMapperImple implements ElementosMapper {

    @Override
    public Elementos toEntity(ElementoDto dto) {
        if (dto == null) {
            return null;
        }
        
        Elementos elementos = new Elementos();
        elementos.setId(dto.getId_elemen());
        elementos.setNom_elemento(dto.getNom_eleme());
        elementos.setObser(dto.getObse());
        elementos.setNum_serie(dto.getNum_seri());
        elementos.setComponentes(dto.getComponen());

        return elementos;
    }

    @Override
    public ElementoDto toDTO(Elementos entity) {
        if (entity == null) {
            return null;
        }
        
        ElementoDto elementoDto = new ElementoDto();
        elementoDto.setId_elemen(entity.getId());
        elementoDto.setNom_eleme(entity.getNom_elemento());
        elementoDto.setObse(entity.getObser());
        elementoDto.setNum_seri(entity.getNum_serie());
        elementoDto.setComponen(entity.getComponentes());
        
        return elementoDto;
    }
}