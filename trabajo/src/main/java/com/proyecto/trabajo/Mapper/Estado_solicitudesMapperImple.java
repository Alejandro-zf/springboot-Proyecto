package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.Estado_solicitudesDto;
import com.proyecto.trabajo.models.Estado_solicitudes;
import com.proyecto.trabajo.models.Solicitudes;

@Component
public class Estado_solicitudesMapperImple implements Estado_solicitudesMapper {

    @Override
    public Estado_solicitudesDto toDTO(Solicitudes solicitudes) {
        if (solicitudes == null) return null;
        Estado_solicitudes estadoSolicitudes = solicitudes.getEstado_solicitudes();
        Long idEstado = estadoSolicitudes != null && estadoSolicitudes.getId() != null 
            ? estadoSolicitudes.getId().longValue() 
            : null;
        return new Estado_solicitudesDto(
            idEstado,
            null,
            solicitudes.getId()
        );
    }

    @Override
    public void applyEstadoFromDto(Solicitudes solicitudes, Estado_solicitudesDto dto) {
        if (dto == null || solicitudes == null) return;
        if (dto.getId_estad() != null) {
            Estado_solicitudes estadoSolicitudes = new Estado_solicitudes();
            estadoSolicitudes.setId(dto.getId_estad().intValue());
            solicitudes.setEstado_solicitudes(estadoSolicitudes);
        }
    }
}
