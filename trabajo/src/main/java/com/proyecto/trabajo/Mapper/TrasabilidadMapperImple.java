package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TrasabilidadCreateDtos;
import com.proyecto.trabajo.dto.TrasabilidadDtos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Trasabilidad;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class TrasabilidadMapperImple implements TrasabilidadMapper {

    private final UsuariosRepository usuariosRepository;
    private final TicketsRepository ticketsRepository;

    public TrasabilidadMapperImple(UsuariosRepository usuariosRepository, TicketsRepository ticketsRepository) {
        this.usuariosRepository = usuariosRepository;
        this.ticketsRepository = ticketsRepository;
    }

    @Override
    public Trasabilidad toTrasabilidad(TrasabilidadDtos dto) {
        if (dto == null) return null;
        Trasabilidad entity = new Trasabilidad();
        // id, fecha, observacion
        try {
            java.lang.reflect.Field fId = Trasabilidad.class.getDeclaredField("id");
            fId.setAccessible(true);
            fId.set(entity, dto.getId_trsa());
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fFecha = Trasabilidad.class.getDeclaredField("fecha");
            fFecha.setAccessible(true);
            fFecha.set(entity, dto.getFech());
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fObs = Trasabilidad.class.getDeclaredField("observacion");
            fObs.setAccessible(true);
            fObs.set(entity, dto.getObser());
        } catch (Exception ignored) {}

        if (dto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(dto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            try {
                java.lang.reflect.Field fUsu = Trasabilidad.class.getDeclaredField("usuario");
                fUsu.setAccessible(true);
                fUsu.set(entity, usuario);
            } catch (Exception ignored) {}
        }
        if (dto.getId_ticet() != null) {
            Tickets tickets = ticketsRepository.findById(dto.getId_ticet())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            try {
                java.lang.reflect.Field fTick = Trasabilidad.class.getDeclaredField("tickets");
                fTick.setAccessible(true);
                fTick.set(entity, tickets);
            } catch (Exception ignored) {}
        }
        return entity;
    }

    @Override
    public TrasabilidadDtos toTrasabilidadDto(Trasabilidad entity) {
        if (entity == null) return null;
        TrasabilidadDtos dto = new TrasabilidadDtos();
        try {
            java.lang.reflect.Field fId = Trasabilidad.class.getDeclaredField("id");
            fId.setAccessible(true);
            dto.setId_trsa((Long) fId.get(entity));
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fFecha = Trasabilidad.class.getDeclaredField("fecha");
            fFecha.setAccessible(true);
            dto.setFech((java.time.LocalDate) fFecha.get(entity));
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fObs = Trasabilidad.class.getDeclaredField("observacion");
            fObs.setAccessible(true);
            dto.setObser((String) fObs.get(entity));
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fUsu = Trasabilidad.class.getDeclaredField("usuario");
            fUsu.setAccessible(true);
            Usuarios usu = (Usuarios) fUsu.get(entity);
            if (usu != null) {
                dto.setId_usu(usu.getId());
                dto.setNom_us(usu.getNom_usu());
            }
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fTick = Trasabilidad.class.getDeclaredField("tickets");
            fTick.setAccessible(true);
            Tickets t = (Tickets) fTick.get(entity);
            if (t != null) {
                dto.setId_ticet(t.getId());
            }
        } catch (Exception ignored) {}
        return dto;
    }

    @Override
    public Trasabilidad toTrasabilidadFromCreateDto(TrasabilidadCreateDtos createDto) {
        if (createDto == null) return null;
        Trasabilidad entity = new Trasabilidad();
        try {
            java.lang.reflect.Field fFecha = Trasabilidad.class.getDeclaredField("fecha");
            fFecha.setAccessible(true);
            fFecha.set(entity, createDto.getFech());
        } catch (Exception ignored) {}
        try {
            java.lang.reflect.Field fObs = Trasabilidad.class.getDeclaredField("observacion");
            fObs.setAccessible(true);
            fObs.set(entity, createDto.getObser());
        } catch (Exception ignored) {}
        if (createDto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(createDto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            try {
                java.lang.reflect.Field fUsu = Trasabilidad.class.getDeclaredField("usuario");
                fUsu.setAccessible(true);
                fUsu.set(entity, usuario);
            } catch (Exception ignored) {}
        }
        if (createDto.getId_ticet() != null) {
            Tickets tickets = ticketsRepository.findById(createDto.getId_ticet())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            try {
                java.lang.reflect.Field fTick = Trasabilidad.class.getDeclaredField("tickets");
                fTick.setAccessible(true);
                fTick.set(entity, tickets);
            } catch (Exception ignored) {}
        }
        return entity;
    }
}
