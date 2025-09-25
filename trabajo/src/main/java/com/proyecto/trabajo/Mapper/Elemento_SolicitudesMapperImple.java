package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.Elemento_SolicitudesDtos;
import com.proyecto.trabajo.models.Elemento_Solicitudes;
import com.proyecto.trabajo.models.Elemento_Solicitudesid;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.SolicitudesRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class Elemento_SolicitudesMapperImple implements Elemento_SolicitudesMapper {

    private final SolicitudesRepository solicitudesRepository;
    private final ElementosRepository elementosRepository;

    public Elemento_SolicitudesMapperImple(SolicitudesRepository solicitudesRepository, ElementosRepository elementosRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.elementosRepository = elementosRepository;
    }

    @Override
    public Elemento_Solicitudes toElemento_Solicitudes(Elemento_SolicitudesDtos dto) {
        Solicitudes solicitudes = solicitudesRepository.findById(dto.getId_Soli())
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        Elementos elementos = elementosRepository.findById(dto.getId_element())
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));

        Elemento_Solicitudesid id = new Elemento_Solicitudesid(dto.getId_Soli(), dto.getId_element());

        Elemento_Solicitudes es = new Elemento_Solicitudes();
        es.setId(id);
        es.setSolicitudes(solicitudes);
        es.setElementos(elementos);
        return es;
    }

    @Override
    public Elemento_SolicitudesDtos toDTO(Elemento_Solicitudes entity) {
        return new Elemento_SolicitudesDtos(
            entity.getElementos().getId(),
            entity.getElementos().getNom_elemento(),
            entity.getSolicitudes().getId()
        );
    }
}
