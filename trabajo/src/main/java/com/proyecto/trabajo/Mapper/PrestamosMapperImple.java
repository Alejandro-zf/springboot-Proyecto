package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.models.Prestamos;

@Component
public class PrestamosMapperImple implements PrestamosMapper {

    @Override
    public Prestamos toEntity(PrestamosDto dto) {
        if (dto == null) {
            return null;
        }
        Prestamos entity = new Prestamos();
        entity.setId(dto.getId_prest());
        entity.setFecha_entre(dto.getFecha_entreg());
        entity.setFecha_recep(dto.getFecha_repc());
        entity.setTipo_prest(dto.getTipo_pres());
        return entity;
    }

    @Override
    public PrestamosDto toDTO(Prestamos entity) {
        if (entity == null) {
            return null;
        }
        PrestamosDto dto = new PrestamosDto();
        dto.setId_prest(entity.getId());
        dto.setFecha_entreg(entity.getFecha_entre());
        dto.setFecha_repc(entity.getFecha_recep());
        dto.setTipo_pres(entity.getTipo_prest());
        return dto;
    }
}
