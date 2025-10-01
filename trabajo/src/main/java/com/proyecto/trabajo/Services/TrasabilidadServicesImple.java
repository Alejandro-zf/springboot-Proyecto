package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.TrasabilidadMapper;
import com.proyecto.trabajo.dto.TrasabilidadCreateDtos;
import com.proyecto.trabajo.dto.TrasabilidadDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Trasabilidad;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.TrasabilidadRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TrasabilidadServicesImple implements TrasabilidadServices {

    private final TrasabilidadRepository trasabilidadRepository;
    private final UsuariosRepository usuariosRepository;
    private final TicketsRepository ticketsRepository;
    private final TrasabilidadMapper trasabilidadMapper;

    public TrasabilidadServicesImple(
        TrasabilidadRepository trasabilidadRepository,
        UsuariosRepository usuariosRepository,
        TicketsRepository ticketsRepository,
        TrasabilidadMapper trasabilidadMapper
    ) {
        this.trasabilidadRepository = trasabilidadRepository;
        this.usuariosRepository = usuariosRepository;
        this.ticketsRepository = ticketsRepository;
        this.trasabilidadMapper = trasabilidadMapper;
    }

    @Override
    @Transactional
    public TrasabilidadDtos guardar(TrasabilidadCreateDtos dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalArgumentException("id_usu es obligatorio");
        }
        if (dto.getId_ticet() == null) {
            throw new IllegalArgumentException("id_ticet es obligatorio");
        }
        Trasabilidad entity = trasabilidadMapper.toTrasabilidadFromCreateDto(dto);
        Trasabilidad guardado = trasabilidadRepository.save(entity);
        return trasabilidadMapper.toTrasabilidadDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TrasabilidadDtos buscarPorId(Long id) {
        Trasabilidad entity = trasabilidadRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Trasabilidad no encontrada"));
        return trasabilidadMapper.toTrasabilidadDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrasabilidadDtos> listarTodos() {
        return trasabilidadRepository.findAll()
            .stream()
            .map(trasabilidadMapper::toTrasabilidadDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Trasabilidad entity = trasabilidadRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Trasabilidad no encontrada"));
        trasabilidadRepository.delete(entity);
    }

    @Override
    @Transactional
    public TrasabilidadDtos actualizar(TrasabilidadDtos dto) {
        Trasabilidad entity = trasabilidadRepository.findById(dto.getId_trsa())
            .orElseThrow(() -> new EntityNotFoundException("Trasabilidad no encontrada"));

        // Actualizar campos simples via reflección (no hay setters en la entidad)
        try {
            if (dto.getFech() != null) {
                java.lang.reflect.Field fFecha = Trasabilidad.class.getDeclaredField("fecha");
                fFecha.setAccessible(true);
                fFecha.set(entity, dto.getFech());
            }
            if (dto.getObser() != null) {
                java.lang.reflect.Field fObs = Trasabilidad.class.getDeclaredField("observacion");
                fObs.setAccessible(true);
                fObs.set(entity, dto.getObser());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error actualizando campos de Trasabilidad", e);
        }

        // Actualizar relaciones si vienen en el DTO
        if (dto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(dto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            try {
                java.lang.reflect.Field fUsu = Trasabilidad.class.getDeclaredField("usuario");
                fUsu.setAccessible(true);
                fUsu.set(entity, usuario);
            } catch (Exception e) {
                throw new RuntimeException("Error asignando usuario en Trasabilidad", e);
            }
        }
        if (dto.getId_ticet() != null) {
            Tickets ticket = ticketsRepository.findById(dto.getId_ticet())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            try {
                java.lang.reflect.Field fTick = Trasabilidad.class.getDeclaredField("tickets");
                fTick.setAccessible(true);
                fTick.set(entity, ticket);
            } catch (Exception e) {
                throw new RuntimeException("Error asignando ticket en Trasabilidad", e);
            }
        }

        Trasabilidad actualizado = trasabilidadRepository.save(entity);
        return trasabilidadMapper.toTrasabilidadDto(actualizado);
    }
}
