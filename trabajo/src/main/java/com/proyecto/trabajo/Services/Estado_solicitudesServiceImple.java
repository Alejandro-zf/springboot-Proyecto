package com.proyecto.trabajo.Services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.Estado_solicitudesMapper;
import com.proyecto.trabajo.dto.Estado_solicitudesDto;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.repository.SolicitudesRepository;
import com.proyecto.trabajo.repository.PrestamosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Estado_solicitudesServiceImple implements Estado_solicitudesService {

    private static final byte ESTADO_APROBADO = 1;

    private final SolicitudesRepository solicitudesRepository;
    private final Estado_solicitudesMapper mapper;
    private final PrestamosRepository prestamosRepository;

    public Estado_solicitudesServiceImple(SolicitudesRepository solicitudesRepository, Estado_solicitudesMapper mapper,
            PrestamosRepository prestamosRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.mapper = mapper;
        this.prestamosRepository = prestamosRepository;
    }

    @Override
    public Estado_solicitudesDto actualizarEstado(Estado_solicitudesDto dto) {
        if (dto == null || dto.getId_soli() == null) {
            throw new IllegalArgumentException("Debe proporcionar id_soli");
        }
        Solicitudes solicitudes = solicitudesRepository.findById(dto.getId_soli())
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        mapper.applyEstadoFromDto(solicitudes, dto);
        boolean aprobado = dto.getId_estad() != null && dto.getId_estad().byteValue() == ESTADO_APROBADO;
        boolean sinPrestamo = solicitudes.getPrestamos() == null || solicitudes.getPrestamos().isEmpty();

        solicitudes = solicitudesRepository.save(solicitudes);

        if (aprobado && sinPrestamo && solicitudes.getUsuario() != null && solicitudes.getEspacio() != null) {
            Prestamos p = new Prestamos();
            p.setFecha_entre(LocalDateTime.now());
            p.setTipo_prest("AUTO");
            p.setUsuario(solicitudes.getUsuario());
            p.setEspacio(solicitudes.getEspacio());
            p.setSolicitudes(solicitudes);
            prestamosRepository.save(p);
        }
        return mapper.toDTO(solicitudes);
    }

    @Override
    public Estado_solicitudesDto obtenerEstado(Long solicitudId) {
        Solicitudes solicitudes = solicitudesRepository.findById(solicitudId)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
        return mapper.toDTO(solicitudes);
    }
}
