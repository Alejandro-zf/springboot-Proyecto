package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

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
        
        entity.setId(dto.getId_trsa());
        entity.setFecha(dto.getFech());
        entity.setObservacion(dto.getObser());

        if (dto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(dto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            entity.setUsuario(usuario);
        }
        
        if (dto.getId_ticet() != null) {
            Tickets tickets = ticketsRepository.findById(dto.getId_ticet())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            entity.setTickets(tickets);
        }
        
        return entity;
    }

    @Override
    public TrasabilidadDtos toTrasabilidadDto(Trasabilidad entity) {
        if (entity == null) return null;
        TrasabilidadDtos dto = new TrasabilidadDtos();
        
        dto.setId_trsa(entity.getId());
        dto.setFech(entity.getFecha());
        dto.setObser(entity.getObservacion());
        
        if (entity.getUsuario() != null) {
            dto.setId_usu(entity.getUsuario().getId());
            dto.setNom_us(entity.getUsuario().getNom_usu());
        }
        
        if (entity.getTickets() != null) {
            dto.setId_ticet(entity.getTickets().getId());
        }
        
        return dto;
    }

    @Override
    public Trasabilidad toTrasabilidadFromCreateDto(TrasabilidadCreateDtos createDto) {
        if (createDto == null) return null;
        Trasabilidad entity = new Trasabilidad();
        
        entity.setFecha(createDto.getFech());
        entity.setObservacion(createDto.getObser());
        
        if (createDto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(createDto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            entity.setUsuario(usuario);
        }
        
        if (createDto.getId_ticet() != null) {
            Tickets tickets = ticketsRepository.findById(createDto.getId_ticet())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            entity.setTickets(tickets);
        }
        
        return entity;
    }

    @Override
    public List<TrasabilidadDtos> toTrasabilidadDtoList(List<Trasabilidad> trasabilidades) {
        if (trasabilidades == null) {
            return List.of();
        }
        
        List<TrasabilidadDtos> trasabilidadDtos = new ArrayList<>(trasabilidades.size());
        for (Trasabilidad trasabilidad : trasabilidades) {
            trasabilidadDtos.add(toTrasabilidadDto(trasabilidad));
        }
        
        return trasabilidadDtos;
    }
}
