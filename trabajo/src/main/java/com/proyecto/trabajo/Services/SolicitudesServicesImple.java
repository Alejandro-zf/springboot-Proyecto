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
import com.proyecto.trabajo.models.Accesorios;
import com.proyecto.trabajo.models.Accesorios_solicitudes;
import com.proyecto.trabajo.repository.SolicitudesRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.PrestamosRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.AccesoriosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitudesServicesImple implements SolicitudesServices {

    private static final byte ESTADO_APROBADO = 1;

    private final SolicitudesRepository solicitudesRepository;
    private final SolicitudesMapper solicitudesMapper;
    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;
    private final PrestamosRepository prestamosRepository;
    private final ElementosRepository elementosRepository;
    private final AccesoriosRepository accesoriosRepository;

    public SolicitudesServicesImple(SolicitudesRepository solicitudesRepository, SolicitudesMapper solicitudesMapper,
            UsuariosRepository usuariosRepository, EspacioRepository espacioRepository,
            PrestamosRepository prestamosRepository, ElementosRepository elementosRepository, AccesoriosRepository accesoriosRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.solicitudesMapper = solicitudesMapper;
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.prestamosRepository = prestamosRepository;
        this.elementosRepository = elementosRepository;
        this.accesoriosRepository = accesoriosRepository;
    }

    @Override
    @Transactional
    public SolicitudesDto guardar(SolicitudeCreateDto dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalArgumentException("id_usu es obligatorio");
        }
        Solicitudes solicitudes = solicitudesMapper.toSolicitudesFromCreateDto(dto);
        // Enlazar Elemento si el mapper no lo hizo
        if (dto.getId_elem() != null && (solicitudes.getElemento() == null || solicitudes.getElemento().isEmpty())) {
            Elementos elemento = elementosRepository.findById(dto.getId_elem())
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
            Elemento_Solicitudes es = new Elemento_Solicitudes();
            es.setSolicitudes(solicitudes);
            es.setElementos(elemento);
            solicitudes.getElemento().add(es);
        }
        // Enlazar Accesorio si el mapper no lo hizo
        if (dto.getId_acces() != null && (solicitudes.getSolicitudesacce() == null || solicitudes.getSolicitudesacce().isEmpty())) {
            Accesorios accesorio = accesoriosRepository.findById(dto.getId_acces().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Accesorio no encontrado"));
            Accesorios_solicitudes as = new Accesorios_solicitudes();
            as.setSolicitudes(solicitudes);
            as.setAccesorios(accesorio);
            solicitudes.getSolicitudesacce().add(as);
        }
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
    public SolicitudesDto actualizarSolicitud(Long id, SolicitudesUpdateDtos dto) {
        Solicitudes solicitudes = solicitudesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        // aplicar actualizaciones parciales usando el mapper
        solicitudesMapper.updateSolicitudesFromUpdateDto(dto, solicitudes);

        // Si la solicitud quedó aprobada y no tiene préstamo, crear uno automáticamente
        boolean aprobado = dto != null && dto.getEst_soli() != null && dto.getEst_soli() == ESTADO_APROBADO;
        boolean sinPrestamo = solicitudes.getPrestamos() == null || solicitudes.getPrestamos().isEmpty();

        Solicitudes actualizado = solicitudesRepository.save(solicitudes);

        if (aprobado && sinPrestamo) {
            Prestamos p = new Prestamos();
            p.setFecha_entre(LocalDateTime.now());
            p.setTipo_prest("AUTO");
            p.setUsuario(actualizado.getUsuario());
            p.setEspacio(actualizado.getEspacio());
            p.setSolicitudes(actualizado);
            prestamosRepository.save(p);
        }
        return solicitudesMapper.toSolicitudesDto(actualizado);
    }
}
