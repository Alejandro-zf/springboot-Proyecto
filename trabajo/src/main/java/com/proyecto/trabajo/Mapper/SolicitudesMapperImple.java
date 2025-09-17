package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;

@Component
public class SolicitudesMapperImple implements SolicitudesMapper {

    @Override
    public Solicitudes toSolicitudes(SolicitudesDto solicitudesdto) {
        if (solicitudesdto == null) {
            return null;
        }
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setId(solicitudesdto.getId_soli());
        solicitudes.setCantidad(solicitudesdto.getCant());
        solicitudes.setFecha_inicio(solicitudesdto.getFecha_ini());
        solicitudes.setFecha_fin(solicitudesdto.getFecha_fn());
        solicitudes.setAmbiente(solicitudesdto.getAmbient());
        
        return solicitudes;
    }

    @Override
    public SolicitudesDto toSolicitudesDTO(Solicitudes solicitudes) {
        if (solicitudes == null) {
            return null;
        }
        SolicitudesDto solicitudesdto = new SolicitudesDto();
        solicitudesdto.setId_soli(solicitudes.getId());
        solicitudesdto.setCant(solicitudes.getCantidad());
        solicitudesdto.setFecha_ini(solicitudes.getFecha_inicio());
        solicitudesdto.setFecha_fn(solicitudes.getFecha_fin());
        solicitudesdto.setAmbient(solicitudes.getAmbiente());
        return solicitudesdto;
    }
}