package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.SolicitudesMapper;
import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.repository.SolicitudesRepository;

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
        return solicitudesMapper.toDTO(solicitudesRepository.save(entity));
    }

    @Override
    public SolicitudesDto buscarPorId(Long id) {
        Solicitudes entity = solicitudesRepository.findById(id).orElse(null);
        return solicitudesMapper.toDTO(entity);
    }

    @Override
    public List<SolicitudesDto> listarTodos() {
        List<Solicitudes> entities = solicitudesRepository.findAll();
        return entities.stream().map(solicitudesMapper::toDTO).toList();
    }

    @Override
    public void eliminar(Long id) {
        solicitudesRepository.deleteById(id);
    }
}
