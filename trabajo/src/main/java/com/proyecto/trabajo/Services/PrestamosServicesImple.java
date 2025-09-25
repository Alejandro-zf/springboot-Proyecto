package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.PrestamosMapper;
import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.dto.PrestamosCreateDto;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.repository.PrestamosRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.EspacioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PrestamosServicesImple implements PrestamosServices {

    private final PrestamosRepository prestamosRepository;
    private final PrestamosMapper prestamosMapper;
    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;

    public PrestamosServicesImple(PrestamosRepository prestamosRepository, PrestamosMapper prestamosMapper,
            UsuariosRepository usuariosRepository, EspacioRepository espacioRepository) {
        this.prestamosRepository = prestamosRepository;
        this.prestamosMapper = prestamosMapper;
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
    }

    @Override
    @Transactional
    public PrestamosDto guardar(PrestamosCreateDto dto) {
        Prestamos prestamos = prestamosMapper.toPrestamosFromCreateDto(dto);
        Prestamos guardado = prestamosRepository.save(prestamos);
        return prestamosMapper.toPrestamosDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamosDto buscarPorId(Long id) {
        Prestamos prestamos = prestamosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        return prestamosMapper.toPrestamosDto(prestamos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamosDto> listarTodos() {
        return prestamosRepository.findAll()
                .stream()
                .map(prestamosMapper::toPrestamosDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Prestamos prestamos = prestamosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        prestamosRepository.delete(prestamos);
    }

    @Override
    @Transactional
    public PrestamosDto actualizarPrestamo(PrestamosDto dto) {
    Prestamos prestamos = prestamosRepository.findById(dto.getId_prest())
        .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

    prestamos.setTipo_prest(dto.getTipo_pres());
    prestamos.setFecha_entre(dto.getFecha_entreg());
    prestamos.setFecha_recep(dto.getFecha_repc());

    if (dto.getId_usu() != null) {
        Usuarios usuario = usuariosRepository.findById(dto.getId_usu())
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        prestamos.setUsuario(usuario);
    }
    if (dto.getId_espac() != null) {
        Espacio espacio = espacioRepository.findById(dto.getId_espac().intValue())
            .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
        prestamos.setEspacio(espacio);
    }

    Prestamos actualizado = prestamosRepository.save(prestamos);
    return prestamosMapper.toPrestamosDto(actualizado);
    }
}
