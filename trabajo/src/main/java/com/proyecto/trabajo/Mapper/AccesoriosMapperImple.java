package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.AccesoriosDto;
import com.proyecto.trabajo.models.Accesorios;

@Component
public class AccesoriosMapperImple implements AccesoriosMapper {

    @Override
    public Accesorios toEntity(AccesoriosDto dto) {
        if (dto == null) {
            return null;
        }
        Accesorios entity = new Accesorios();
        entity.setId(dto.getId_accesorio());
        entity.setCant(dto.getCanti());
        entity.setNom_acce(dto.getNom_acces());
        entity.setMarca(dto.getMarc());
        entity.setNum_serie(dto.getNum_ser());
        return entity;
    }

    @Override
    public AccesoriosDto toDTO(Accesorios entity) {
        if (entity == null) {
            return null;
        }
        AccesoriosDto dto = new AccesoriosDto();
        dto.setId_accesorio(entity.getId());
        dto.setCanti(entity.getCant());
        dto.setNom_acces(entity.getNom_acce());
        dto.setMarc(entity.getMarca());
        dto.setNum_ser(entity.getNum_serie());
        return dto;
    }
}
