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

@Component
public class PrestamosMapperImple implements PrestamosMapper {

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
        return prestamosDto;
    }

    @Override
    public Prestamos toPrestamosFromCreateDto(PrestamosCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Prestamos prestamos = new Prestamos();
        prestamos.setFecha_entre(createDto.getFecha_entreg());
        prestamos.setFecha_recep(createDto.getFecha_repc());
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
        return prestamos;
    }
}