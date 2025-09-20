package com.proyecto.trabajo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.dto.Prestamos_ElementoDto;
import com.proyecto.trabajo.Mapper.Prestamos_ElementoMapper;
import com.proyecto.trabajo.models.Prestamos_Elemento;
import com.proyecto.trabajo.models.Prestamos_Elementoid;
import com.proyecto.trabajo.repository.PrestamosElementoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Prestamos_ElementoServiceImple implements Prestamos_ElementoService {

    private final PrestamosElementoRepository repository;
    private final Prestamos_ElementoMapper mapper;

    public Prestamos_ElementoServiceImple(PrestamosElementoRepository repository, Prestamos_ElementoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Prestamos_ElementoDto asignar(Prestamos_ElementoDto dto) {
        Prestamos_Elemento pe = mapper.toPrestamos_Elemento(dto);
        Prestamos_Elementoid id = new Prestamos_Elementoid(dto.getPrestamosId(), dto.getElementoId());
        if (repository.existsById(id)) {
            throw new IllegalStateException("Ya existe una asignación para ese préstamo y elemento");
        }
        Prestamos_Elemento guardado = repository.save(pe);
        return mapper.toDTO(guardado);
    }

    @Override
    public List<Prestamos_ElementoDto> listarPorPrestamo(Long prestamosId) {
        return repository.findByPrestamos_Id(prestamosId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Prestamos_ElementoDto> listarPorElemento(Long elementoId) {
        return repository.findByElementos_Id(elementoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarAsignacion(Long prestamosId, Long elementoId) {
        Prestamos_Elementoid id = new Prestamos_Elementoid(prestamosId, elementoId);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Asignación no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Prestamos_ElementoDto> asignarElementos(List<Prestamos_ElementoDto> lista) {
        List<Prestamos_ElementoDto> elementos = new ArrayList<>();
        for (Prestamos_ElementoDto dto : lista) {
            try {
                Prestamos_ElementoDto asignado = asignar(dto);
                elementos.add(asignado);
            } catch (Exception e) {
                System.out.println("Error al asignar elemento " + dto.getElementoId() + ": " + e.getMessage());
            }
        }
        return elementos;
    }
}
