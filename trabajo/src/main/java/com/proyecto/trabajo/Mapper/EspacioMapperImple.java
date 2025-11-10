package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.EspacioCreateDto;
import com.proyecto.trabajo.dto.EspacioDto;
import com.proyecto.trabajo.models.Espacio;

@Component
public class EspacioMapperImple implements EspacioMapper {

    @Override
    public Espacio toEspacio(EspacioDto dto) {
        if (dto == null) return null;
        Espacio entity = new Espacio();
        
        entity.setId(dto.getId());
        entity.setNom_espa(dto.getNom_espa());
        entity.setDescripcion(dto.getDescripcion());
        entity.setEstadoespacio(dto.getEstadoespacio());
        entity.setImagenes(dto.getImagenes());
        
        return entity;
    }

    @Override
    public EspacioDto toEspacioDto(Espacio entity) {
        if (entity == null) return null;
        EspacioDto dto = new EspacioDto();
        
        dto.setId(entity.getId());
        dto.setNom_espa(entity.getNom_espa());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEstadoespacio(entity.getEstadoespacio());
        dto.setImagenes(entity.getImagenes());
        
        return dto;
    }

    @Override
    public Espacio toEspacioFromCreateDto(EspacioCreateDto createDto) {
        if (createDto == null) return null;
        Espacio entity = new Espacio();
        
        entity.setNom_espa(createDto.getNom_espa());
        entity.setDescripcion(createDto.getDescripcion());
        entity.setEstadoespacio(createDto.getEstadoespacio());
        entity.setImagenes(createDto.getImagenes());
        
        return entity;
    }

    @Override
    public List<EspacioDto> toEspacioDtoList(List<Espacio> espacios) {
        if (espacios == null) {
            return List.of();
        }
        
        List<EspacioDto> espacioDtos = new ArrayList<>(espacios.size());
        for (Espacio espacio : espacios) {
            espacioDtos.add(toEspacioDto(espacio));
        }
        
        return espacioDtos;
    }
}
