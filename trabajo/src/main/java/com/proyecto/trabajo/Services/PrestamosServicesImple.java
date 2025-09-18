package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.PrestamosMapper;
import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.repository.PrestamosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PrestamosServicesImple implements PrestamosServices {

    private final PrestamosRepository prestamosRepository;
    private final PrestamosMapper prestamosMapper;

    public PrestamosServicesImple(PrestamosRepository prestamosRepository, PrestamosMapper prestamosMapper) {
        this.prestamosRepository = prestamosRepository;
        this.prestamosMapper = prestamosMapper;
    }

    @Override
    public PrestamosDto guardar(PrestamosDto dto) {
        Prestamos entity = prestamosMapper.toEntity(dto);
        Prestamos guardado = prestamosRepository.save(entity);
        return prestamosMapper.toDTO(guardado);
    }

    @Override
    public PrestamosDto buscarPorId(Long id) {
        return prestamosRepository.findById(id)
                .map(prestamosMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Prestamo no encontrado"));
    }

    @Override
    public List<PrestamosDto> listarTodos() {
        return prestamosRepository.findAll()
                .stream()
                .map(prestamosMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        prestamosRepository.deleteById(id);
    }
}