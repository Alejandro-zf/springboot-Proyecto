package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.Accesorios_SolicitudesDtos;
import com.proyecto.trabajo.models.Accesorios;
import com.proyecto.trabajo.models.Accesorios_solicitudes;
import com.proyecto.trabajo.models.Accesorios_solicitudesid;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.repository.AccesoriosRepository;
import com.proyecto.trabajo.repository.SolicitudesRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class Accesorios_SolicitudesMapperImple implements Accesorios_SolicitudesMapper {

    private final SolicitudesRepository solicitudesRepository;
    private final AccesoriosRepository accesoriosRepository;

    public Accesorios_SolicitudesMapperImple(SolicitudesRepository solicitudesRepository, AccesoriosRepository accesoriosRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.accesoriosRepository = accesoriosRepository;
    }

    @Override
    public Accesorios_solicitudes toEntity(Accesorios_SolicitudesDtos dto) {
        Solicitudes solicitudes = solicitudesRepository.findById(dto.getId_soli())
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        Accesorios accesorio = accesoriosRepository.findById(dto.getId_acces().intValue())
            .orElseThrow(() -> new EntityNotFoundException("Accesorio no encontrado"));

        Accesorios_solicitudesid id = new Accesorios_solicitudesid(
            dto.getId_soli(),
            dto.getId_acces().intValue()
        );

        Accesorios_solicitudes as = new Accesorios_solicitudes();
        as.setId(id);
        as.setSolicitudes(solicitudes);
        as.setAccesorios(accesorio);
        return as;
    }

    @Override
    public Accesorios_SolicitudesDtos toDTO(Accesorios_solicitudes entity) {
        Accesorios_SolicitudesDtos dto = new Accesorios_SolicitudesDtos();
        dto.setId_soli(entity.getSolicitudes().getId());
        if (entity.getAccesorios() != null) {
            dto.setId_acces(entity.getAccesorios().getId().longValue());
            dto.setNom_acces(entity.getAccesorios().getNom_acce());
        }
        return dto;
    }
}
