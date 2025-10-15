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
import com.proyecto.trabajo.models.Accesorios;
import com.proyecto.trabajo.models.Accesorios_solicitudes;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.AccesoriosRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;

import jakarta.persistence.EntityNotFoundException;

@Component
public class SolicitudesMapperImple implements SolicitudesMapper {

    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;
    private final ElementosRepository elementosRepository;
    private final AccesoriosRepository accesoriosRepository;
    public SolicitudesMapperImple(UsuariosRepository usuariosRepository, EspacioRepository espacioRepository, ElementosRepository elementosRepository, AccesoriosRepository accesoriosRepository) {
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.elementosRepository = elementosRepository;
        this.accesoriosRepository = accesoriosRepository;
    }

    @Override
    public Solicitudes toSolicitudes(SolicitudesDto solicitudesDto) {
        Usuarios usuario = usuariosRepository.findById(solicitudesDto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Espacio espacio = espacioRepository.findById(solicitudesDto.getId_espa().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));

        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setId(solicitudesDto.getId_soli());
        solicitudes.setFecha_inicio(solicitudesDto.getFecha_ini());
        solicitudes.setFecha_fin(solicitudesDto.getFecha_fn());
        solicitudes.setAmbiente(solicitudesDto.getAmbient());
        solicitudes.setNum_ficha(solicitudesDto.getNum_fich());
        Byte est = solicitudesDto.getEst_soli();
        solicitudes.setEstadosolicitud(est != null ? est : 1);
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
        dto.setEst_soli(entity.getEstadosolicitud());
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
        if (entity.getSolicitudesacce() != null && !entity.getSolicitudesacce().isEmpty()) {
            StringBuilder accesIds = new StringBuilder();
            StringBuilder accesNames = new StringBuilder();
            boolean firstAcc = true;
            for (Accesorios_solicitudes as : entity.getSolicitudesacce()) {
                if (as != null && as.getAccesorios() != null) {
                    if (!firstAcc) {
                        accesIds.append(",");
                        accesNames.append(", ");
                    }
                    if (as.getAccesorios().getId() != null) {
                        accesIds.append(as.getAccesorios().getId());
                    }
                    if (as.getAccesorios().getNom_acce() != null) {
                        accesNames.append(as.getAccesorios().getNom_acce());
                    }
                    firstAcc = false;
                }
            }
            dto.setAcces_id(accesIds.toString());
            dto.setNom_acces(accesNames.toString());
        } else {
            dto.setAcces_id("");
            dto.setNom_acces("");
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
        Byte est = createDto.getEstadosoli();
        // Por defecto = 2 (no aprobado) si no viene en el DTO
        solicitudes.setEstadosolicitud(est != null ? est : 2);
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
        if (createDto.getIds_acces() != null && !createDto.getIds_acces().isEmpty()) {
            Set<Long> uniqueAccIds = new LinkedHashSet<>(createDto.getIds_acces());
            for (Long idAcc : uniqueAccIds) {
                if (idAcc == null) continue;
                Accesorios accesorio = accesoriosRepository.findById(idAcc.intValue())
                        .orElseThrow(() -> new EntityNotFoundException("Accesorio no encontrado"));
                Accesorios_solicitudes as = new Accesorios_solicitudes();
                as.setSolicitudes(solicitudes);
                as.setAccesorios(accesorio);
                solicitudes.getSolicitudesacce().add(as);
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
        if (updateDto.getEst_soli() != null) {
            entity.setEstadosolicitud(updateDto.getEst_soli());
        }
    }
}