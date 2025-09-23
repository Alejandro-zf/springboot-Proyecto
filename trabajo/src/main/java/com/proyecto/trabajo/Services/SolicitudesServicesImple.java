package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public SolicitudesDto guardar(SolicitudesDto dto) {
        Solicitudes solicitudes = solicitudesMapper.toSolicitudes(dto);
        Solicitudes guardado = solicitudesRepository.save(solicitudes);
        return solicitudesMapper.toSolicitudesDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudesDto buscarPorId(Long id) {
        Solicitudes solicitudes = solicitudesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
        return solicitudesMapper.toSolicitudesDto(solicitudes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudesDto> listarTodos() {
        return solicitudesRepository.findAll()
                .stream()
                .map(solicitudesMapper::toSolicitudesDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Solicitudes solicitudes = solicitudesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
        solicitudesRepository.delete(solicitudes);
    }

    @Override
    @Transactional
    public SolicitudesDto actualizarSolicitud(SolicitudesDto dto) {
        Solicitudes solicitudes = solicitudesRepository.findById(dto.getId_soli())
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
        
    solicitudes.setCantidad(dto.getCant());
    solicitudes.setFecha_inicio(dto.getFecha_ini());
    solicitudes.setFecha_fin(dto.getFecha_fn());
    solicitudes.setAmbiente(dto.getAmbient());
    // solicitudes.setEstado(dto.getObse()); // Eliminado porque no existe en el DTO
        
        Solicitudes actualizado = solicitudesRepository.save(solicitudes);
        return solicitudesMapper.toSolicitudesDto(actualizado);
    }
}
