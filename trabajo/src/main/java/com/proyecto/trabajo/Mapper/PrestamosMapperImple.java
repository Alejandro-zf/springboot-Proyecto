package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.dto.PrestamosCreateDto;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.EspacioRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

@Component
public class PrestamosMapperImple implements PrestamosMapper {

    private static final Set<String> TIPOS_VALIDOS = new HashSet<>(
        Arrays.asList("Portátiles", "Equipo de mesa", "Televisores")
    );

    private static void validarTipo(String tipo) {
        if (tipo == null || !TIPOS_VALIDOS.contains(tipo)) {
            throw new IllegalArgumentException(
                "Tipo de préstamo inválido. Debe ser uno de: Portátiles, Equipo de mesa, Televisores");
        }
    }

    @Override
    public Prestamos fromSolicitudAprobada(com.proyecto.trabajo.models.Solicitudes solicitud) {
        if (solicitud == null) return null;
        Prestamos p = new Prestamos();
        p.setFecha_entre(java.time.LocalDateTime.now());
        p.setTipo_prest("Portátiles");
        p.setUsuario(solicitud.getUsuario());
        p.setEspacio(solicitud.getEspacio());
        p.setSolicitudes(solicitud);
        return p;
    }

    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;

    public PrestamosMapperImple(UsuariosRepository usuariosRepository, EspacioRepository espacioRepository) {
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
    }

    @Override
    public Prestamos toPrestamos(PrestamosDto prestamosDto) {
        if (prestamosDto == null) {
            return null;
        }
        Prestamos prestamos = new Prestamos();
        prestamos.setId(prestamosDto.getId_prest());
        prestamos.setFecha_entre(prestamosDto.getFecha_entreg());
        prestamos.setFecha_recep(prestamosDto.getFecha_repc());
        validarTipo(prestamosDto.getTipo_pres());
        prestamos.setTipo_prest(prestamosDto.getTipo_pres());
        if (prestamosDto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(prestamosDto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            prestamos.setUsuario(usuario);
        }
        if (prestamosDto.getId_espac() != null) {
            Espacio espacio = espacioRepository.findById(prestamosDto.getId_espac().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
            prestamos.setEspacio(espacio);
        }
        return prestamos;
    }

    @Override
    public Prestamos toPrestamosFromCreateDto(PrestamosCreateDto createDto) {
        if (createDto == null) return null;
        Prestamos prestamos = new Prestamos();
        prestamos.setFecha_entre(createDto.getFecha_entreg());
        prestamos.setFecha_recep(createDto.getFecha_repc());
        validarTipo(createDto.getTipo_pres());
        prestamos.setTipo_prest(createDto.getTipo_pres());

        if (createDto.getId_usuario() != null) {
            Usuarios usuario = usuariosRepository.findById(createDto.getId_usuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            prestamos.setUsuario(usuario);
        }

        if (createDto.getId_esp() != null) {
            Espacio espacio = espacioRepository.findById(createDto.getId_esp().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
            prestamos.setEspacio(espacio);
        }

        // Note: mapping of elementos and accesorios by id (id_elem, id_acces) is outside
        // the responsibilities of this mapper in the original code. If needed, add
        // repositories and map them here similar to usuario/espacio lookup.

        return prestamos;
    }

    @Override
    public PrestamosDto toPrestamosDto(Prestamos prestamos) {
        if (prestamos == null) {
            return null;
        }
        PrestamosDto prestamosDto = new PrestamosDto();
        prestamosDto.setId_prest(prestamos.getId());
        prestamosDto.setFecha_entreg(prestamos.getFecha_entre());
        prestamosDto.setFecha_repc(prestamos.getFecha_recep());
        prestamosDto.setTipo_pres(prestamos.getTipo_prest());
        if (prestamos.getUsuario() != null) {
            prestamosDto.setId_usu(prestamos.getUsuario().getId());
            prestamosDto.setNom_usu(prestamos.getUsuario().getNom_usu());
        }
        if (prestamos.getEspacio() != null) {
            prestamosDto.setId_espac(prestamos.getEspacio().getId().longValue());
            prestamosDto.setNom_espac(prestamos.getEspacio().getNom_espa());
        }
        // Mapear elemento asociado (primer elemento) si existe
        if (prestamos.getPrestamoss() != null && !prestamos.getPrestamoss().isEmpty()) {
            com.proyecto.trabajo.models.Prestamos_Elemento pe = prestamos.getPrestamoss().get(0);
            if (pe != null && pe.getElementos() != null) {
                prestamosDto.setId_elem(pe.getElementos().getId());
                prestamosDto.setNom_elem(pe.getElementos().getNom_elemento());
            }
        }
        // Mapear accesorio asociado (primer accesorio) si existe
        if (prestamos.getAccesoriosprestamo() != null && !prestamos.getAccesoriosprestamo().isEmpty()) {
            com.proyecto.trabajo.models.Accesorios_Prestamos ap = prestamos.getAccesoriosprestamo().get(0);
            if (ap != null && ap.getAccesorios() != null) {
                prestamosDto.setId_acceso(ap.getAccesorios().getId().longValue());
                prestamosDto.setNom_aces(ap.getAccesorios().getNom_acce());
            }
        }
        return prestamosDto;
    }
}