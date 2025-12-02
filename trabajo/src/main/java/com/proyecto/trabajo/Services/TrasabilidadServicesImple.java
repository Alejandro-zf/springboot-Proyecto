
package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

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
    @Override
    public List<TrasabilidadDtos> buscarPorTicketId(Long ticketId) {
        List<Trasabilidad> lista = trasabilidadRepository.findByTicketsId(ticketId);
        return trasabilidadMapper.toTrasabilidadDtoList(lista);
    }

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

    private void validarTrasabilidad(TrasabilidadCreateDtos dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        if (dto.getId_ticet() == null) {
            throw new IllegalArgumentException("El ticket es obligatorio");
        }
        if (dto.getObser() != null && dto.getObser().length() > 255) {
            throw new IllegalArgumentException("La observación no puede exceder 255 caracteres");
        }
    }

    @Override
    public TrasabilidadDtos guardar(TrasabilidadCreateDtos dto) {
        validarTrasabilidad(dto);
        
        Trasabilidad entity = trasabilidadMapper.toTrasabilidadFromCreateDto(dto);
        Trasabilidad guardado = trasabilidadRepository.save(entity);
        return trasabilidadMapper.toTrasabilidadDto(guardado);
    }

    @Override
    public TrasabilidadDtos buscarPorId(Long id) {
        return trasabilidadRepository.findById(id)
                .map(trasabilidadMapper::toTrasabilidadDto)
                .orElseThrow(() -> new EntityNotFoundException("Trasabilidad no encontrada con id: " + id));
    }

    @Override
    public List<TrasabilidadDtos> listarTodos() {
        List<Trasabilidad> trasabilidades = trasabilidadRepository.findAll();
        return trasabilidadMapper.toTrasabilidadDtoList(trasabilidades);
    }

    @Override
    public void eliminar(Long id) {
        if (!trasabilidadRepository.existsById(id)) {
            throw new EntityNotFoundException("Trasabilidad no encontrada con id: " + id);
        }
        trasabilidadRepository.deleteById(id);
    }

    @Override
    public TrasabilidadDtos actualizar(TrasabilidadDtos dto) {
        Trasabilidad entity = trasabilidadRepository.findById(dto.getId_trsa())
                .orElseThrow(() -> new EntityNotFoundException("Trasabilidad no encontrada con id: " + dto.getId_trsa()));

        
        if (dto.getFech() != null) {
            entity.setFecha(dto.getFech());
        }
        if (dto.getObser() != null) {
            entity.setObservacion(dto.getObser());
        }

        
        if (dto.getId_usu() != null) {
            Usuarios usuario = usuariosRepository.findById(dto.getId_usu())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + dto.getId_usu()));
            entity.setUsuario(usuario);
        }
        
        if (dto.getId_ticet() != null) {
            Tickets ticket = ticketsRepository.findById(dto.getId_ticet())
                    .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado con id: " + dto.getId_ticet()));
            entity.setTickets(ticket);
        }

        Trasabilidad actualizado = trasabilidadRepository.save(entity);
        return trasabilidadMapper.toTrasabilidadDto(actualizado);
    }
}
