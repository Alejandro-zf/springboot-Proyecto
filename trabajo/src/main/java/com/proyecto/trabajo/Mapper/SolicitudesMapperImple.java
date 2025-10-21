package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.dto.SolicitudeCreateDto;
import com.proyecto.trabajo.dto.SolicitudesUpdateDtos;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Elemento_Solicitudes;
import com.proyecto.trabajo.models.Estado_solicitudes;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.Estado_solicitudesRepository;
import java.util.Set;
import java.util.LinkedHashSet;

import jakarta.persistence.EntityNotFoundException;

@Component
public class SolicitudesMapperImple implements SolicitudesMapper {

    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;
    private final ElementosRepository elementosRepository;
    private final Estado_solicitudesRepository estadoSolicitudesRepository;

    public SolicitudesMapperImple(UsuariosRepository usuariosRepository, EspacioRepository espacioRepository, ElementosRepository elementosRepository, Estado_solicitudesRepository estadoSolicitudesRepository) {
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.elementosRepository = elementosRepository;
        this.estadoSolicitudesRepository = estadoSolicitudesRepository;
    }

    @Override
    public Solicitudes toSolicitudes(SolicitudesDto solicitudesDto) {
        Usuarios usuario = usuariosRepository.findById(solicitudesDto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Espacio espacio = null;
        if (solicitudesDto.getId_espa() != null) {
            try {
                Integer idEspacio = Integer.parseInt(solicitudesDto.getId_espa().toString());
                espacio = espacioRepository.findById(idEspacio)
                        .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("id_espa debe ser numérico", e);
            }
        }

        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setId(solicitudesDto.getId_soli());
        solicitudes.setFecha_inicio(solicitudesDto.getFecha_ini());
        solicitudes.setFecha_fin(solicitudesDto.getFecha_fn());
        solicitudes.setAmbiente(solicitudesDto.getAmbient());
        solicitudes.setNum_ficha(solicitudesDto.getNum_fich());
        if (solicitudesDto.getEst_soli() != null) {
            try {
                Integer idEstado = Integer.parseInt(solicitudesDto.getEst_soli());
                Estado_solicitudes estadoSolicitudes = estadoSolicitudesRepository.findById(idEstado)
                        .orElseThrow(() -> new EntityNotFoundException("Estado de solicitud no encontrado"));
                solicitudes.setEstado_solicitudes(estadoSolicitudes);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("est_soli debe ser numérico", e);
            }
        }
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
        dto.setFecha_ini(entity.getFecha_inicio());
        dto.setFecha_fn(entity.getFecha_fin());
        dto.setAmbient(entity.getAmbiente());
        dto.setNum_fich(entity.getNum_ficha());
        if (entity.getEstado_solicitudes() != null) {
            String nombreEstado = entity.getEstado_solicitudes().getNom_esta();
            if (nombreEstado != null && !nombreEstado.isEmpty()) {
                dto.setEst_soli(nombreEstado);
            } else {
                switch (entity.getEstado_solicitudes().getId()) {
                    case 1:
                        dto.setEst_soli("Activo");
                        break;
                    case 2:
                        dto.setEst_soli("Inactivo");
                        break;
                    case 3:
                        dto.setEst_soli("Pendiente");
                        break;
                    default:
                        dto.setEst_soli("Desconocido");
                }
            }
        }
        if (entity.getUsuario() != null) {
            dto.setId_usu(entity.getUsuario().getId());
            dto.setNom_usu(entity.getUsuario().getNom_usu());
        }
        if (entity.getEspacio() != null) {
            dto.setId_espa(entity.getEspacio().getId().longValue());
            dto.setNom_espa(entity.getEspacio().getNom_espa());
        }
        // Mapear IDs y nombres de elementos como strings concatenados ("2,3,4")
        if (entity.getElemento() != null && !entity.getElemento().isEmpty()) {
            StringBuilder idsJoin = new StringBuilder();
            StringBuilder namesJoin = new StringBuilder();
            boolean first = true;
            for (Elemento_Solicitudes es : entity.getElemento()) {
                if (es != null && es.getElementos() != null) {
                    Elementos el = es.getElementos();
                    if (!first) {
                        idsJoin.append(",");
                        namesJoin.append(", ");
                    }
                    if (el.getId() != null) {
                        idsJoin.append(el.getId());
                    }
                    if (el.getNom_elemento() != null) {
                        namesJoin.append(el.getNom_elemento());
                    }
                    first = false;
                }
            }
            dto.setId_elem(idsJoin.toString());
            dto.setNom_elem(namesJoin.toString());
        }
        return dto;
    }

    @Override
    public Solicitudes toSolicitudesFromCreateDto(SolicitudeCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setFecha_inicio(createDto.getFecha_ini());
        solicitudes.setFecha_fin(createDto.getFecha_fn());
        solicitudes.setAmbiente(createDto.getAmbient());
        solicitudes.setNum_ficha(createDto.getNum_fich());
    // Por defecto = 2 (no aprobado) si no viene en el DTO, si viene 1 (aprobado) se usa ese valor
    // 1=aprobado, 2=pendiente, 3=denegado
    Integer estadoId = (createDto.getId_estado_soli() != null) ? createDto.getId_estado_soli() : 2;
    Estado_solicitudes estadoSolicitudes = estadoSolicitudesRepository.findById(estadoId)
        .orElseThrow(() -> new EntityNotFoundException("Estado de solicitud no encontrado"));
    solicitudes.setEstado_solicitudes(estadoSolicitudes);
    // Sincroniza el campo estadosolicitud para la lógica de préstamo automático
    solicitudes.setEstadosolicitud(estadoId.byteValue());
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
        if (createDto.getIds_elem() != null && !createDto.getIds_elem().isEmpty()) {
            Set<Long> uniqueElemIds = new LinkedHashSet<>(createDto.getIds_elem());
            for (Long idElem : uniqueElemIds) {
                if (idElem == null) continue;
                Elementos elemento = elementosRepository.findById(idElem)
                        .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
                Elemento_Solicitudes es = new Elemento_Solicitudes();
                es.setSolicitudes(solicitudes);
                es.setElementos(elemento);
                solicitudes.getElemento().add(es);
            }
        }
        return solicitudes;
    }

    @Override
    public void updateSolicitudesFromUpdateDto(SolicitudesUpdateDtos updateDto, Solicitudes entity) {
        if (updateDto == null || entity == null) {
            return;
        }
        if (updateDto.getFecha_ini() != null) {
            entity.setFecha_inicio(updateDto.getFecha_ini());
        }
        if (updateDto.getFecha_fn() != null) {
            entity.setFecha_fin(updateDto.getFecha_fn());
        }
        if (updateDto.getId_est_soli() != null) {
            Estado_solicitudes estadoSolicitudes = estadoSolicitudesRepository.findById(updateDto.getId_est_soli())
                    .orElseThrow(() -> new EntityNotFoundException("Estado de solicitud no encontrado"));
            entity.setEstado_solicitudes(estadoSolicitudes);
        }
    }
}