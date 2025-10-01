package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.Estado_solicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;

@Component
public class Estado_solicitudesMapperImple implements Estado_solicitudesMapper {

    @Override
    public Estado_solicitudesDto toDTO(Solicitudes solicitudes) {
        if (solicitudes == null) return null;
        Byte estado = solicitudes.getEstadosolicitud();
        Long idEstadoAsLong = estado != null ? estado.longValue() : null;
        return new Estado_solicitudesDto(
            idEstadoAsLong,
            null,
            solicitudes.getId()
        );
    }

    @Override
    public void applyEstadoFromDto(Solicitudes solicitudes, Estado_solicitudesDto dto) {
        if (dto == null || solicitudes == null) return;
        if (dto.getId_estad() != null) {
            solicitudes.setEstadosolicitud(dto.getId_estad().byteValue());
        }
    }
}
