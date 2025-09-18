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

    public SolicitudesMapperImple(UsuariosRepository usuariosRepository, 
                                  EspacioRepository espacioRepository,
                                  EstadoSolicitudesRepository estadoSolicitudesRepository) {
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.estadoSolicitudesRepository = estadoSolicitudesRepository;
    }

    @Override
    public Solicitudes toSolicitudes(SolicitudesDto solicitudesDto) {
        if (solicitudesDto == null) {
            return null;
        }
        
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setId(solicitudesDto.getId_soli());
        solicitudes.setCantidad(solicitudesDto.getCant());
        solicitudes.setFecha_inicio(solicitudesDto.getFecha_ini());
        solicitudes.setFecha_fin(solicitudesDto.getFecha_fn());
        solicitudes.setAmbiente(solicitudesDto.getAmbient());
        solicitudes.setEstado(solicitudesDto.getObse());

        // Usuario obligatorio
        if (solicitudesDto.getId_usuario() != null) {
            Usuarios usuario = usuariosRepository.findById(solicitudesDto.getId_usuario())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            solicitudes.setUsuario(usuario);
        }
        if (solicitudesDto.getId_espacio() != null) {
            Espacio espacio = espacioRepository.findById(solicitudesDto.getId_espacio())
                    .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
            solicitudes.setEspacio(espacio);
        }
        if (solicitudesDto.getId_estado_soli() != null) {
            Estado_solicitudes estadoSolicitudes = estadoSolicitudesRepository.findById(solicitudesDto.getId_estado_soli())
                    .orElseThrow(() -> new EntityNotFoundException("Estado de solicitud no encontrado"));
            solicitudes.setEstado_solicitudes(estadoSolicitudes);
        }
        
        return solicitudes;
    }

    @Override
    public SolicitudesDto toSolicitudesDTO(Solicitudes solicitudes) {
        if (solicitudes == null) {
            return null;
        }
        
        SolicitudesDto solicitudesDto = new SolicitudesDto();
        solicitudesDto.setId_soli(solicitudes.getId());
        solicitudesDto.setCant(solicitudes.getCantidad());
        solicitudesDto.setFecha_ini(solicitudes.getFecha_inicio());
        solicitudesDto.setFecha_fn(solicitudes.getFecha_fin());
        solicitudesDto.setAmbient(solicitudes.getAmbiente());
        solicitudesDto.setObse(solicitudes.getEstado());
        
        // Relaciones
        solicitudesDto.setId_usuario(solicitudes.getUsuario() != null ? solicitudes.getUsuario().getId() : null);
        solicitudesDto.setId_espacio(solicitudes.getEspacio() != null ? solicitudes.getEspacio().getId() : null);
        solicitudesDto.setId_estado_soli(solicitudes.getEstado_solicitudes() != null ? solicitudes.getEstado_solicitudes().getId_estado_soli() : null);
        
        return solicitudesDto;
    }
}