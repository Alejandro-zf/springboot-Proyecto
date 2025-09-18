package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public PrestamosDto guardar(PrestamosDto dto) {
        Prestamos prestamos = prestamosMapper.toPrestamos(dto);
        Prestamos guardado = prestamosRepository.save(prestamos);
        return prestamosMapper.toPrestamosDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamosDto buscarPorId(Long id) {
        Prestamos prestamos = prestamosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        return prestamosMapper.toPrestamosDto(prestamos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamosDto> listarTodos() {
        return prestamosRepository.findAll()
                .stream()
                .map(prestamosMapper::toPrestamosDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Prestamos prestamos = prestamosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        prestamosRepository.delete(prestamos);
    }

    @Override
    @Transactional
    public PrestamosDto actualizarPrestamo(PrestamosDto dto) {
        Prestamos prestamos = prestamosRepository.findById(dto.getId_prest())
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        
        prestamos.setTipo_prest(dto.getTipo_prest());
        prestamos.setFecha_entre(dto.getFecha_entre());
        prestamos.setFecha_recep(dto.getFecha_recep());
        
        Prestamos actualizado = prestamosRepository.save(prestamos);
        return prestamosMapper.toPrestamosDto(actualizado);
    }
}