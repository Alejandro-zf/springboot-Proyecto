package com.proyecto.trabajo.Services;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.Estado_solicitudesMapper;
import com.proyecto.trabajo.dto.Estado_solicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.repository.SolicitudesRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Estado_solicitudesServiceImple implements Estado_solicitudesService {

    private final SolicitudesRepository solicitudesRepository;
    private final Estado_solicitudesMapper mapper;

    public Estado_solicitudesServiceImple(SolicitudesRepository solicitudesRepository, Estado_solicitudesMapper mapper) {
        this.solicitudesRepository = solicitudesRepository;
        this.mapper = mapper;
    }

    @Override
    public Estado_solicitudesDto actualizarEstado(Estado_solicitudesDto dto) {
        if (dto == null || dto.getId_soli() == null) {
            throw new IllegalArgumentException("Debe proporcionar id_soli");
        }
        Solicitudes solicitudes = solicitudesRepository.findById(dto.getId_soli())
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        mapper.applyEstadoFromDto(solicitudes, dto);
        solicitudes = solicitudesRepository.save(solicitudes);
        return mapper.toDTO(solicitudes);
    }

    @Override
    public Estado_solicitudesDto obtenerEstado(Long solicitudId) {
        Solicitudes solicitudes = solicitudesRepository.findById(solicitudId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
        return mapper.toDTO(solicitudes);
    }
}
