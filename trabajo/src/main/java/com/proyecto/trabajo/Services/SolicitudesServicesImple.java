package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.SolicitudesMapper;
import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.repository.SolicitudesRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitudesServicesImple implements SolicitudesServices {

    private final SolicitudesRepository solicitudesRepository;
    private final SolicitudesMapper solicitudesMapper;

    public SolicitudesServicesImple(SolicitudesRepository solicitudesRepository, SolicitudesMapper solicitudesMapper) {
        this.solicitudesRepository = solicitudesRepository;
        this.solicitudesMapper = solicitudesMapper;
    }

    @Override
    public SolicitudesDto guardar(SolicitudesDto dto) {
        Solicitudes entity = solicitudesMapper.toEntity(dto);
        Solicitudes guardado = solicitudesRepository.save(entity);
        return solicitudesMapper.toDTO(guardado);
    }

    @Override
    public SolicitudesDto buscarPorId(Long id) {
        return solicitudesRepository.findById(id)
                .map(solicitudesMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
    }

    @Override
    public List<SolicitudesDto> listarTodos() {
        return solicitudesRepository.findAll()
                .stream()
                .map(solicitudesMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        solicitudesRepository.deleteById(id);
    }
}