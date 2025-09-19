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
        if (solicitudesDto == null)
            return null;
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setId(solicitudesDto.getId_soli());
        solicitudes.setCantidad(solicitudesDto.getCant());
        solicitudes.setFecha_inicio(solicitudesDto.getFecha_ini());
        solicitudes.setFecha_fin(solicitudesDto.getFecha_fn());
        solicitudes.setAmbiente(solicitudesDto.getAmbient());
        try {
            Usuarios usuario = usuariosRepository.findById(1L)
                    .orElseThrow(() -> new EntityNotFoundException("No encontrado elemento"));
            solicitudes.setUsuario(usuario);
        } catch (Exception e) {
            throw new EntityNotFoundException("No encontrado elemento");
        }
        try {
            Espacio espacio = espacioRepository.findById(1)
                    .orElseThrow(() -> new EntityNotFoundException("No encontrado espacio"));
            solicitudes.setEspacio(espacio);
        } catch (Exception e) {
            throw new EntityNotFoundException("No encontrado espacio");
        }
        try {
            Estado_solicitudes estadoSolicitudes = estadoSolicitudesRepository.findById(1)
                    .orElseThrow(() -> new EntityNotFoundException("Estado de solicitudes no encontrado"));
            solicitudes.setEstado_solicitudes(estadoSolicitudes);
        } catch (Exception e) {
            throw new EntityNotFoundException("Estado de solicitudes no encontrado");
        }

        return solicitudes;
    }
    @Override
    public SolicitudesDto toSolicitudesDto(Solicitudes solicitudes) {
        if (solicitudes == null)
            return null;

        SolicitudesDto solicitudesDto = new SolicitudesDto();
        solicitudesDto.setId_soli(solicitudes.getId());
        solicitudesDto.setCant(solicitudes.getCantidad());
        solicitudesDto.setFecha_ini(solicitudes.getFecha_inicio());
        solicitudesDto.setFecha_fn(solicitudes.getFecha_fin());
        solicitudesDto.setAmbient(solicitudes.getAmbiente());
        return solicitudesDto;
    }
}