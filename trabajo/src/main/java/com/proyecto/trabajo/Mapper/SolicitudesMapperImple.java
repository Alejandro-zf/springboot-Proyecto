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
        if (entity == null) {
            return null;
        }
        SolicitudesDto dto = new SolicitudesDto();
        dto.setId_soli(entity.getId());
        dto.setCant(entity.getCantidad());
        dto.setFecha_ini(entity.getFecha_inicio());
        dto.setFecha_fn(entity.getFecha_fin());
        dto.setAmbient(entity.getAmbiente());
        dto.setEst_soli(entity.getEstadosolicitud());
        if (entity.getUsuario() != null) {
            dto.setId_usu(entity.getUsuario().getId());
            dto.setNom_usu(entity.getUsuario().getNom_usu());
        }
        if (entity.getEspacio() != null) {
            dto.setId_espa(entity.getEspacio().getId().longValue());
            dto.setNom_espa(entity.getEspacio().getNom_espa());
        }
        return dto;
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
        solicitudes.setEstadosolicitud(createDto.getEstadosoli());
        if (createDto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(createDto.getId_usu())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            solicitudes.setUsuario(usuario);
        }
        if (createDto.getId_esp() != null) {
            Espacio espacio = espacioRepository.findById(createDto.getId_esp().intValue())
                    .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
            solicitudes.setEspacio(espacio);
        }
        return solicitudes;
    }
}