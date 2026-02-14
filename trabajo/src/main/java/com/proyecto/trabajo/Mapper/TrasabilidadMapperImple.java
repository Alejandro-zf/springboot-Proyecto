package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.TrasabilidadCreateDtos;
import com.proyecto.trabajo.dto.TrasabilidadDtos;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Trasabilidad;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.ElementosRepository;

import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Component
public class TrasabilidadMapperImple implements TrasabilidadMapper {

    private final UsuariosRepository usuariosRepository;
    private final TicketsRepository ticketsRepository;
    private final com.proyecto.trabajo.repository.ElementosRepository elementosRepository;

    public TrasabilidadMapperImple(UsuariosRepository usuariosRepository, TicketsRepository ticketsRepository, ElementosRepository elementosRepository) {
        this.usuariosRepository = usuariosRepository;
        this.ticketsRepository = ticketsRepository;
        this.elementosRepository = elementosRepository;
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
        
        // obser: solo la observación de la trasabilidad
        if (entity.getTickets() != null) {
            dto.setId_ticet(entity.getTickets().getId());
        }
        dto.setObser(entity.getObservacion());
        if (entity.getUsuario() != null) {
            dto.setId_usu(entity.getUsuario().getId());
            dto.setNom_us(entity.getUsuario().getNom_usu());
            dto.setNum_doc(entity.getUsuario().getNum_doc());
        }
        if (entity.getTickets() != null && entity.getTickets().getUsuario() != null) {
            dto.setId_usu_reporta(entity.getTickets().getUsuario().getId());
            dto.setNom_us_reporta(entity.getTickets().getUsuario().getNom_usu());
            dto.setNum_doc_reporta(entity.getTickets().getUsuario().getNum_doc());
        }
        
        if (entity.getTickets() != null) {
            if (entity.getTickets().getElementos() != null) {
                dto.setId_elemen(entity.getTickets().getElementos().getId());
                dto.setNom_elemen(entity.getTickets().getElementos().getNom_elemento());
            }
            if (entity.getTickets().getTicketProblemas() != null && !entity.getTickets().getTicketProblemas().isEmpty()) {
                String problemasStr = entity.getTickets().getTicketProblemas().stream()
                    .map(tp -> tp.getProblema() != null ? tp.getProblema().getDesc_problema() : "")
                    .collect(Collectors.joining(", "));
                dto.setNom_problm(problemasStr);
            }
        }
        
        return dto;
    }

    @Override
    public Trasabilidad toTrasabilidadFromCreateDto(TrasabilidadCreateDtos createDto) {
        if (createDto == null) return null;
        Trasabilidad entity = new Trasabilidad();
        
        entity.setFecha(createDto.getFech());
        entity.setObservacion(createDto.getObse());
        
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
        
        if (createDto.getId_elemen() != null) {
            com.proyecto.trabajo.models.Elementos elemento = elementosRepository.findById(createDto.getId_elemen())
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
            entity.setElementos(elemento);
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

    @Override
    public void updateTrasabilidadFromUpdateDto(com.proyecto.trabajo.dto.TrasabilidadUpdateDtos updateDto, Trasabilidad entity) {
        if (updateDto == null || entity == null) return;

        if (updateDto.getFech() != null) {
            entity.setFecha(updateDto.getFech());
        }

        if (updateDto.getObser() != null) {
            entity.setObservacion(updateDto.getObser());
        }

        if (updateDto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(updateDto.getId_usu())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            entity.setUsuario(usuario);
        }

        if (updateDto.getId_ticet() != null) {
            Tickets tickets = ticketsRepository.findById(updateDto.getId_ticet())
                    .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            entity.setTickets(tickets);
        }

        if (updateDto.getId_elemen() != null) {
            if (this.elementosRepository == null) {
                throw new IllegalStateException("ElementosRepository no disponible en TrasabilidadMapperImple");
            }
            com.proyecto.trabajo.models.Elementos elemento = elementosRepository.findById(updateDto.getId_elemen())
                    .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
            entity.setElementos(elemento);
        }
    }
}
