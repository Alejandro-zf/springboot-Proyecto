package com.proyecto.trabajo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.Elemento_SolicitudesMapper;
import com.proyecto.trabajo.dto.Elemento_SolicitudesDtos;
import com.proyecto.trabajo.models.Elemento_Solicitudes;
import com.proyecto.trabajo.models.Elemento_Solicitudesid;
import com.proyecto.trabajo.repository.Elemento_SolicitudesRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Elemento_SolicitudesServiceImple implements Elemento_SolicitudesService {

    private final Elemento_SolicitudesRepository repository;
    private final Elemento_SolicitudesMapper mapper;

    public Elemento_SolicitudesServiceImple(Elemento_SolicitudesRepository repository, Elemento_SolicitudesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Elemento_SolicitudesDtos asignar(Elemento_SolicitudesDtos dto) {
        Elemento_Solicitudes es = mapper.toElemento_Solicitudes(dto);
        Elemento_Solicitudesid id = new Elemento_Solicitudesid(dto.getId_Soli(), dto.getId_element());
        if (repository.existsById(id)) {
            throw new IllegalStateException("Ya existe una asignación para esa solicitud y elemento");
        }
        Elemento_Solicitudes guardado = repository.save(es);
        return mapper.toDTO(guardado);
    }

    @Override
    public List<Elemento_SolicitudesDtos> listarPorSolicitud(Long solicitudId) {
        return repository.findBySolicitudes_Id(solicitudId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Elemento_SolicitudesDtos> listarPorElemento(Long elementoId) {
        return repository.findByElementos_Id(elementoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarAsignacion(Long solicitudId, Long elementoId) {
        Elemento_Solicitudesid id = new Elemento_Solicitudesid(solicitudId, elementoId);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Asignación no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Elemento_SolicitudesDtos> asignarElementos(List<Elemento_SolicitudesDtos> dtos) {
        List<Elemento_SolicitudesDtos> asignados = new ArrayList<>();
        for (Elemento_SolicitudesDtos dto : dtos) {
            try {
                Elemento_SolicitudesDtos asignado = asignar(dto);
                asignados.add(asignado);
            } catch (Exception e) {
                System.out.println("Error al asignar elemento " + dto.getId_element() + ": " + e.getMessage());
            }
        }
        return asignados;
    }
}
