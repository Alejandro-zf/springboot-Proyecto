package com.proyecto.trabajo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.dto.Tickets_elementoDto;
import com.proyecto.trabajo.Mapper.Tickets_elementoMapper;
import com.proyecto.trabajo.models.Tickets_elemento;
import com.proyecto.trabajo.models.Tickets_elementoid;
import com.proyecto.trabajo.repository.Tickets_elementoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class Tickets_elementoServiceImple implements Tickets_elementoService {

    private final Tickets_elementoRepository repository;
    private final Tickets_elementoMapper mapper;

    public Tickets_elementoServiceImple(Tickets_elementoRepository repository, Tickets_elementoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Tickets_elementoDto asignar(Tickets_elementoDto dto) {
        Tickets_elemento te = mapper.toTickets_elemento(dto);
        Tickets_elementoid id = new Tickets_elementoid(dto.getId_Ticket(), dto.getId_element());
        if (repository.existsById(id)) {
            throw new IllegalStateException("Ya existe una asignación para ese ticket y elemento");
        }
        Tickets_elemento guardado = repository.save(te);
        return mapper.toTickets_elementoDto(guardado);
    }

    @Override
    public List<Tickets_elementoDto> listarPorTicket(Long ticketId) {
        return repository.findByTickets_Id(ticketId)
                .stream()
                .map(mapper::toTickets_elementoDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tickets_elementoDto> listarPorElemento(Long elementoId) {
        return repository.findByElementos_Id(elementoId)
                .stream()
                .map(mapper::toTickets_elementoDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarAsignacion(Long ticketId, Long elementoId) {
        Tickets_elementoid id = new Tickets_elementoid(ticketId, elementoId);
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Asignación no encontrada");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Tickets_elementoDto> asignarElementos(List<Tickets_elementoDto> lista) {
        List<Tickets_elementoDto> elementos = new ArrayList<>();
        for (Tickets_elementoDto dto : lista) {
            try {
                Tickets_elementoDto asignado = asignar(dto);
                elementos.add(asignado);
            } catch (Exception e) {
                System.out.println("Error al asignar elemento " + dto.getId_element() + ": " + e.getMessage());
            }
        }
        return elementos;
    }
}
