package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.dto.SolicitudeCreateDto;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class SolicitudesMapperImple implements SolicitudesMapper {

    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;

    public SolicitudesMapperImple(UsuariosRepository usuariosRepository, EspacioRepository espacioRepository) {
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
    }

    @Override
    public Solicitudes toSolicitudes(SolicitudesDto solicitudesDto) {
        Usuarios usuario = usuariosRepository.findById(solicitudesDto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Espacio espacio = espacioRepository.findById(solicitudesDto.getId_espa().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));

        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setId(solicitudesDto.getId_soli());
        solicitudes.setCantidad(solicitudesDto.getCant());
        solicitudes.setFecha_inicio(solicitudesDto.getFecha_ini());
        solicitudes.setFecha_fin(solicitudesDto.getFecha_fn());
        solicitudes.setAmbiente(solicitudesDto.getAmbient());
        solicitudes.setEstadosolicitud(solicitudesDto.getEst_soli());
        solicitudes.setUsuario(usuario);
        solicitudes.setEspacio(espacio);

        return solicitudes;
    }

    @Override
    public SolicitudesDto toSolicitudesDto(Solicitudes entity) {
        Long idUsu = entity.getUsuario() != null ? entity.getUsuario().getId() : null;
        String nomUsu = entity.getUsuario() != null ? entity.getUsuario().getNom_usu() : null;
        Long idEspa = entity.getEspacio() != null ? entity.getEspacio().getId().longValue() : null;
        String nomEspa = entity.getEspacio() != null ? entity.getEspacio().getNom_espa() : null;

        return new SolicitudesDto(
                entity.getId(),
                entity.getCantidad(),
                entity.getFecha_inicio(),
                entity.getFecha_fin(),
                entity.getAmbiente(),
                entity.getEstadosolicitud(),
                idUsu,
                nomUsu,
                idEspa,
                nomEspa,
                null,
                null
        );
    }

    @Override
    public Solicitudes toSolicitudesFromCreateDto(SolicitudeCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setCantidad(createDto.getCant());
        solicitudes.setFecha_inicio(createDto.getFecha_ini());
        solicitudes.setFecha_fin(createDto.getFecha_fn());
        solicitudes.setAmbiente(createDto.getAmbient());
        return solicitudes;
    }
}