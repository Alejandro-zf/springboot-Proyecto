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
        Arrays.asList("Portátiles", "Equipo de mesa", "Televisores", "AUTO", "Espacios")
    );

    private static void validarTipo(String tipo) {
        if (tipo == null || !TIPOS_VALIDOS.contains(tipo)) {
            throw new IllegalArgumentException(
                "Tipo de préstamo inválido. Debe ser uno de: Portátiles, Equipo de mesa, Televisores, AUTO, Espacios");
        }
    }

    @Override
    public Prestamos fromSolicitudAprobada(com.proyecto.trabajo.models.Solicitudes solicitud) {
        if (solicitud == null) return null;
        Prestamos p = new Prestamos();
        p.setFecha_entre(java.time.LocalDateTime.now());
        
        // Determinar el tipo según lo que tenga la solicitud
        String tipo = "AUTO";
        if (solicitud.getEspacio() != null && (solicitud.getElemento() == null || solicitud.getElemento().isEmpty())) {
            tipo = "Espacios";
        } else if (solicitud.getElemento() != null && !solicitud.getElemento().isEmpty()) {
            // Determinar tipo según la categoría o subcategoría de los elementos
            tipo = "Portátiles"; // Default, podría mejorarse con lógica adicional
        }
        
        p.setTipo_prest(tipo);
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
        prestamos.setEstado(prestamosDto.getEstado());
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
        prestamos.setFecha_entre(createDto.getFechaEntreg());
        prestamos.setFecha_recep(createDto.getFechaRepc());
        validarTipo(createDto.getTipoPres());
        prestamos.setTipo_prest(createDto.getTipoPres());
        if (createDto.getEstado() != null) {
            prestamos.setEstado(createDto.getEstado());
        }

        if (createDto.getIdUsuario() != null) {
            Usuarios usuario = usuariosRepository.findById(createDto.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            prestamos.setUsuario(usuario);
        }

        if (createDto.getIdEsp() != null) {
            Espacio espacio = espacioRepository.findById(createDto.getIdEsp().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
            prestamos.setEspacio(espacio);
        }


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
        prestamosDto.setEstado(prestamos.getEstado());
        if (prestamos.getUsuario() != null) {
            prestamosDto.setId_usu(prestamos.getUsuario().getId());
            prestamosDto.setNom_usu(prestamos.getUsuario().getNom_usu());
        }
        if (prestamos.getEspacio() != null) {
            prestamosDto.setId_espac(prestamos.getEspacio().getId().longValue());
            prestamosDto.setNom_espac(prestamos.getEspacio().getNom_espa());
        }
    if (prestamos.getPrestamoss() != null && !prestamos.getPrestamoss().isEmpty()) {
            StringBuilder idsJoin = new StringBuilder();
            StringBuilder namesJoin = new StringBuilder();
            boolean first = true;
            for (com.proyecto.trabajo.models.Prestamos_Elemento pe : prestamos.getPrestamoss()) {
                if (pe != null && pe.getElementos() != null) {
                    var el = pe.getElementos();
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
            prestamosDto.setId_elem(idsJoin.toString());
            prestamosDto.setNom_elem(namesJoin.toString());
        }
        return prestamosDto;
    }
}