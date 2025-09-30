package com.proyecto.trabajo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.Accesorios_SolicitudesMapper;
import com.proyecto.trabajo.dto.Accesorios_SolicitudesDtos;
import com.proyecto.trabajo.models.Accesorios_solicitudes;
import com.proyecto.trabajo.models.Accesorios_solicitudesid;
import com.proyecto.trabajo.repository.Accesorios_SolicitudesRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Accesorios_SolicitudesServiceImple implements Accesorios_SolicitudesService {

    private final Accesorios_SolicitudesRepository repository;
    private final Accesorios_SolicitudesMapper mapper;

    public Accesorios_SolicitudesServiceImple(Accesorios_SolicitudesRepository repository, Accesorios_SolicitudesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Accesorios_SolicitudesDtos asignar(Accesorios_SolicitudesDtos dto) {
        Accesorios_solicitudes entity = mapper.toEntity(dto);
        Accesorios_solicitudesid id = new Accesorios_solicitudesid(dto.getId_soli(), dto.getId_acces().intValue());
        if (repository.existsById(id)) {
            throw new IllegalStateException("Ya existe una asignación para esa solicitud y accesorio");
        }
        Accesorios_solicitudes guardado = repository.save(entity);
        return mapper.toDTO(guardado);
    }

    @Override
    public List<Accesorios_SolicitudesDtos> listarPorSolicitud(Long solicitudId) {
        return repository.findBySolicitudes_Id(solicitudId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Accesorios_SolicitudesDtos> listarPorAccesorio(Long accesorioId) {
        return repository.findByAccesorios_Id(accesorioId.intValue())
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarAsignacion(Long solicitudId, Long accesorioId) {
        Accesorios_solicitudesid id = new Accesorios_solicitudesid(solicitudId, accesorioId.intValue());
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Asignación no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Accesorios_SolicitudesDtos> asignarAccesorios(List<Accesorios_SolicitudesDtos> dtos) {
        List<Accesorios_SolicitudesDtos> asignados = new ArrayList<>();
        for (Accesorios_SolicitudesDtos dto : dtos) {
            try {
                Accesorios_SolicitudesDtos asignado = asignar(dto);
                asignados.add(asignado);
            } catch (Exception e) {
                System.out.println("Error al asignar accesorio " + dto.getId_acces() + ": " + e.getMessage());
            }
        }
        return asignados;
    }
}
