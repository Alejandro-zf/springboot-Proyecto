package com.proyecto.trabajo.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.SolicitudesMapper;
import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.dto.SolicitudeCreateDto;
import com.proyecto.trabajo.dto.SolicitudesUpdateDtos;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Elemento_Solicitudes;
import com.proyecto.trabajo.models.Prestamos_Elemento;
import com.proyecto.trabajo.repository.SolicitudesRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.PrestamosRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.PrestamosElementoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitudesServicesImple implements SolicitudesServices {


    private final SolicitudesRepository solicitudesRepository;
    private final SolicitudesMapper solicitudesMapper;
    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;
    private final PrestamosRepository prestamosRepository;
    private final ElementosRepository elementosRepository;
    private final com.proyecto.trabajo.Mapper.PrestamosMapper prestamosMapper;
    private final PrestamosElementoRepository prestamosElementoRepository;

    public SolicitudesServicesImple(SolicitudesRepository solicitudesRepository, SolicitudesMapper solicitudesMapper,
            UsuariosRepository usuariosRepository, EspacioRepository espacioRepository,
            PrestamosRepository prestamosRepository, ElementosRepository elementosRepository,
            com.proyecto.trabajo.Mapper.PrestamosMapper prestamosMapper,
            PrestamosElementoRepository prestamosElementoRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.solicitudesMapper = solicitudesMapper;
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.prestamosRepository = prestamosRepository;
        this.elementosRepository = elementosRepository;
        this.prestamosMapper = prestamosMapper;
        this.prestamosElementoRepository = prestamosElementoRepository;
    }

    @Transactional
    public void expirarSolicitudesVencidas() {
        List<Solicitudes> vencidas = solicitudesRepository.findVencidasNoExpiradas(LocalDateTime.now());
        if (vencidas == null || vencidas.isEmpty()) return;
        for (Solicitudes s : vencidas) {
            s.setEstadosolicitud((byte) 3);
            solicitudesRepository.save(s);
            if (s.getElemento() != null) {
                for (Elemento_Solicitudes es : s.getElemento()) {
                    Elementos elem = es.getElementos();
                    if (elem != null) {
                        elem.setEstadosoelement((byte) 1);
                        elementosRepository.save(elem);
                    }
                }
            }
        }
    }

    private void sincronizarEstadoElementos(Solicitudes solicitud) {
        if (solicitud == null || solicitud.getElemento() == null) return;
        Byte estadoSolicitud = solicitud.getEstadosolicitud();
        if (estadoSolicitud == null) return;

        final byte estadoFinal = (estadoSolicitud == 1) ? (byte) 2 : (byte) 1;
        solicitud.getElemento().forEach(es -> {
            Elementos elem = es.getElementos();
            if (elem != null && (elem.getEstadosoelement() == null || elem.getEstadosoelement() != estadoFinal)) {
                elem.setEstadosoelement(estadoFinal);
                elementosRepository.save(elem);
            }
        });
    }

    @Override
    @Transactional
    public SolicitudesDto guardar(SolicitudeCreateDto dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalArgumentException("id_usu es obligatorio");
        }
        Solicitudes solicitudes = solicitudesMapper.toSolicitudesFromCreateDto(dto);
        if (solicitudes.getEstadosolicitud() == null) {
            solicitudes.setEstadosolicitud((byte) 2);
        }

        if (dto.getIds_elem() != null && !dto.getIds_elem().isEmpty()
            && (solicitudes.getElemento() == null || solicitudes.getElemento().isEmpty())) {
            for (Long idElem : dto.getIds_elem()) {
                if (idElem == null) continue;
                Elementos elemento = elementosRepository.findById(idElem)
                    .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
                Elemento_Solicitudes es = new Elemento_Solicitudes();
                es.setSolicitudes(solicitudes);
                es.setElementos(elemento);
                solicitudes.getElemento().add(es);
            }
        }
        if (solicitudes.getUsuario() == null && dto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(dto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            solicitudes.setUsuario(usuario);
        }
        if (solicitudes.getEspacio() == null && dto.getId_esp() != null) {
            Espacio espacio = espacioRepository.findById(dto.getId_esp().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
            solicitudes.setEspacio(espacio);
        }
        if (solicitudes.getUsuario() == null) {
            throw new IllegalArgumentException("El usuario no puede ser null para la solicitud");
        }
        Solicitudes guardado = solicitudesRepository.save(solicitudes);
        Solicitudes solicitudFullPostSave = solicitudesRepository.findById(guardado.getId())
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada tras guardar"));
    expirarSolicitudesVencidas();
    sincronizarEstadoElementos(solicitudFullPostSave);

        if (guardado.getEstadosolicitud() != null && guardado.getEstadosolicitud() == 1) {
            
            boolean sinPrestamo = guardado.getPrestamos() == null || guardado.getPrestamos().isEmpty();
            if (sinPrestamo) {
                Solicitudes solicitudFull = solicitudesRepository.findById(guardado.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada tras guardar"));
                if (solicitudFull.getUsuario() == null) {
                    throw new IllegalArgumentException("No se puede crear préstamo: usuario null en solicitud aprobada");
                }
                Prestamos p = prestamosMapper.fromSolicitudAprobada(solicitudFull);
                Prestamos prestamoGuardado = prestamosRepository.save(p);
                
                if (solicitudFull.getElemento() != null) {
                    for (Elemento_Solicitudes es : solicitudFull.getElemento()) {
                        if (es != null && es.getElementos() != null) {
                            Prestamos_Elemento pe = new Prestamos_Elemento();
                            pe.setPrestamos(prestamoGuardado);
                            pe.setElementos(es.getElementos());
                            pe.setObser_prest("AUTO");
                            prestamosElementoRepository.save(pe);
                        }
                    }
                }
            }
        }
        Solicitudes toReturn = solicitudesRepository.findById(solicitudFullPostSave.getId())
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada al preparar respuesta"));
        return solicitudesMapper.toSolicitudesDto(toReturn);
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudesDto buscarPorId(Long id) {
    expirarSolicitudesVencidas();
    Solicitudes solicitudes = solicitudesRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
        return solicitudesMapper.toSolicitudesDto(solicitudes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudesDto> listarTodos() {
    expirarSolicitudesVencidas();
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
    public SolicitudesDto actualizarSolicitud(Long id, SolicitudesUpdateDtos dto) {
        Solicitudes solicitudes = solicitudesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

    solicitudesMapper.updateSolicitudesFromUpdateDto(dto, solicitudes);
        boolean aprobado = dto != null && dto.getId_est_soli() != null && dto.getId_est_soli() == 1;
        boolean sinPrestamo = solicitudes.getPrestamos() == null || solicitudes.getPrestamos().isEmpty();

        Solicitudes actualizado = solicitudesRepository.save(solicitudes);
        sincronizarEstadoElementos(actualizado);

    expirarSolicitudesVencidas();

        if (aprobado) {
            if (sinPrestamo) {
                boolean crearPrestamo = actualizado.getEstadosolicitud() != null && actualizado.getEstadosolicitud() == 1;
                if (crearPrestamo && actualizado.getUsuario() == null) {
                    throw new IllegalArgumentException("No se puede crear el préstamo automáticamente porque la solicitud no tiene usuario asignado. Asigna un usuario antes de aprobar la solicitud.");
                }
                if (crearPrestamo) {
                    Prestamos p = new Prestamos();
                    p.setFecha_entre(LocalDateTime.now());
                    p.setTipo_prest("AUTO");
                    p.setUsuario(actualizado.getUsuario());
                    p.setEspacio(actualizado.getEspacio());
                    p.setSolicitudes(actualizado);
                    prestamosRepository.save(p);
                }
            }
        }
        return solicitudesMapper.toSolicitudesDto(actualizado);
    }
}

