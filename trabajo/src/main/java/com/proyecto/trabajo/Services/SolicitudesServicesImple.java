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
    public SolicitudesDto guardar(SolicitudesDto solicitudesdto) {
        Solicitudes solicitudes = solicitudesMapper.toSolicitudes(solicitudesdto);
        Solicitudes guardado = solicitudesRepository.save(solicitudes);
        return solicitudesMapper.toSolicitudesDTO(guardado);
    }

    @Override
    public SolicitudesDto buscarPorId(Long id) {
        return solicitudesRepository.findById(id)
                .map(solicitudesMapper::toSolicitudesDTO)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
    }

    @Override
    public List<SolicitudesDto> listarTodos() {
        return solicitudesRepository.findAll()
                .stream()
                .map(solicitudesMapper::toSolicitudesDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        solicitudesRepository.deleteById(id);
    }
}