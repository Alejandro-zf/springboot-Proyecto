package com.proyecto.trabajo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.Accesorios_PrestamosMapper;
import com.proyecto.trabajo.dto.Accesorios_PrestamosDtos;
import com.proyecto.trabajo.models.Accesorios_Prestamos;
import com.proyecto.trabajo.models.Accesorios_Prestamosid;
import com.proyecto.trabajo.repository.Accesorios_PrestamosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Accesorios_PrestamosServiceImple implements Accesorios_PrestamosService {

    private final Accesorios_PrestamosRepository repository;
    private final Accesorios_PrestamosMapper mapper;

    public Accesorios_PrestamosServiceImple(Accesorios_PrestamosRepository repository, Accesorios_PrestamosMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Accesorios_PrestamosDtos asignar(Accesorios_PrestamosDtos dto) {
        Accesorios_Prestamos entity = mapper.toEntity(dto);
        Accesorios_Prestamosid id = new Accesorios_Prestamosid(dto.getId_acceso().intValue(), dto.getId_prest());
        if (repository.existsById(id)) {
            throw new IllegalStateException("Ya existe una asignación para ese accesorio y préstamo");
        }
        Accesorios_Prestamos guardado = repository.save(entity);
        return mapper.toDTO(guardado);
    }

    @Override
    public List<Accesorios_PrestamosDtos> listarPorPrestamo(Long prestamosId) {
        return repository.findByPrestamos_Id(prestamosId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Accesorios_PrestamosDtos> listarPorAccesorio(Long accesorioId) {
        return repository.findByAccesorios_Id(accesorioId.intValue())
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarAsignacion(Long accesorioId, Long prestamosId) {
        Accesorios_Prestamosid id = new Accesorios_Prestamosid(accesorioId.intValue(), prestamosId);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Asignación no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Accesorios_PrestamosDtos> asignarAccesorios(List<Accesorios_PrestamosDtos> dtos) {
        List<Accesorios_PrestamosDtos> asignados = new ArrayList<>();
        for (Accesorios_PrestamosDtos dto : dtos) {
            try {
                Accesorios_PrestamosDtos asignado = asignar(dto);
                asignados.add(asignado);
            } catch (Exception e) {
                System.out.println("Error al asignar accesorio " + dto.getId_acceso() + ": " + e.getMessage());
            }
        }
        return asignados;
    }
}
