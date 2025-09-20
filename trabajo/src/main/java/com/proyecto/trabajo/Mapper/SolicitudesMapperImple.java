package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.models.Estado_solicitudes;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.EstadoSolicitudesRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class SolicitudesMapperImple implements SolicitudesMapper {

    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;
    private final EstadoSolicitudesRepository estadoSolicitudesRepository;

    public SolicitudesMapperImple(UsuariosRepository usuariosRepository, EspacioRepository espacioRepository, EstadoSolicitudesRepository estadoSolicitudesRepository) {
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.estadoSolicitudesRepository = estadoSolicitudesRepository;
    }

    @Override
    public Solicitudes toSolicitudes(SolicitudesDto dto) {
        Usuarios usuario = usuariosRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Espacio espacio = espacioRepository.findById(dto.getEspacioId().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));

        Estado_solicitudes estadoSolicitudes = estadoSolicitudesRepository.findById(dto.getEstadoSolicitudesId().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Estado de solicitudes no encontrado"));

        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setId(dto.getId_soli());
        solicitudes.setCantidad(dto.getCant());
        solicitudes.setFecha_inicio(dto.getFecha_ini());
        solicitudes.setFecha_fin(dto.getFecha_fn());
        solicitudes.setAmbiente(dto.getAmbient());
        solicitudes.setEstado(dto.getObse());
        solicitudes.setUsuario(usuario);
        solicitudes.setEspacio(espacio);
        solicitudes.setEstado_solicitudes(estadoSolicitudes);

        return solicitudes;
    }

    @Override
    public SolicitudesDto toDTO(Solicitudes entity) {
        return new SolicitudesDto(
                entity.getId(),
                entity.getCantidad(),
                entity.getFecha_inicio(),
                entity.getFecha_fin(),
                entity.getAmbiente(),
                entity.getEstado(),
                entity.getUsuario().getId(),
                entity.getEspacio().getId().longValue(),
                entity.getEstado_solicitudes().getId_estado_soli().longValue());
    }
}