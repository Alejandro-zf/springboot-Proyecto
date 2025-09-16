package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;

@Component
public class SolicitudesMapperImple implements SolicitudesMapper {

    @Override
    public Solicitudes toEntity(SolicitudesDto dto) {
        if (dto == null) {
            return null;
        }
        Solicitudes entity = new Solicitudes();
        entity.setId(dto.getId_soli());
        entity.setCantidad(dto.getCant());
        entity.setFecha_inicio(dto.getFecha_ini());
        entity.setFecha_fin(dto.getFecha_fn());
        entity.setAmbiente(dto.getAmbient());
        // Campo estado no existe en DTO; no se mapea
        return entity;
    }

    @Override
    public SolicitudesDto toDTO(Solicitudes entity) {
        if (entity == null) {
            return null;
        }
        SolicitudesDto dto = new SolicitudesDto();
        dto.setId_soli(entity.getId());
        dto.setCant(entity.getCantidad());
        dto.setFecha_ini(entity.getFecha_inicio());
        dto.setFecha_fn(entity.getFecha_fin());
        dto.setAmbient(entity.getAmbiente());
        return dto;
    }
}