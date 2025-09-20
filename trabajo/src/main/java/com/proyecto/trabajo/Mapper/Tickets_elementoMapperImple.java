package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.Tickets_elementoDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Tickets_elemento;
import com.proyecto.trabajo.models.Tickets_elementoid;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.TicketsRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class Tickets_elementoMapperImple implements Tickets_elementoMapper {

    private final TicketsRepository ticketsRepository;
    private final ElementosRepository elementosRepository;

    public Tickets_elementoMapperImple(TicketsRepository ticketsRepository, ElementosRepository elementosRepository) {
        this.ticketsRepository = ticketsRepository;
        this.elementosRepository = elementosRepository;
    }

    @Override
    public Tickets_elemento toTickets_elemento(Tickets_elementoDto dto) {
        if (dto == null)
            return null;
        
        Tickets_elemento te = new Tickets_elemento();
        
        try {
            Tickets tickets = ticketsRepository.findById(1L)
                    .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
            te.setTickets(tickets);
        } catch (Exception e) {
            throw new EntityNotFoundException("Ticket no encontrado");
        }
        
        try {
            Elementos elementos = elementosRepository.findById(1L)
                    .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
            te.setElementos(elementos);
        } catch (Exception e) {
            throw new EntityNotFoundException("Elemento no encontrado");
        }
        
        Tickets_elementoid id = new Tickets_elementoid(1L, 1L);
        te.setId(id);
        te.setObser_ticket(dto.getObs_ticket());
        te.setNum_ticket(dto.getNume_ticket());

        return te;
    }

    @Override
    public Tickets_elementoDto toTickets_elementoDto(Tickets_elemento entity) {
        if (entity == null)
            return null;

        return new Tickets_elementoDto(
                entity.getTickets().getId(),
                entity.getElementos().getId(),
                entity.getObser_ticket(),
                entity.getNum_ticket());
    }
}
