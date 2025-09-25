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

        Tickets tickets = ticketsRepository.findById(dto.getId_Ticket())
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        Elementos elementos = elementosRepository.findById(dto.getId_element())
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));

        Tickets_elementoid id = new Tickets_elementoid(dto.getId_Ticket(), dto.getId_element());

        Tickets_elemento te = new Tickets_elemento();
        te.setId(id);
        te.setTickets(tickets);
        te.setElementos(elementos);
        te.setObser_ticket(dto.getObs_ticket());
        te.setNum_ticket(dto.getNume_ticket());

        return te;
    }

    @Override
    public Tickets_elementoDto toTickets_elementoDto(Tickets_elemento entity) {
        if (entity == null)
            return null;

        return new Tickets_elementoDto(
                entity.getObser_ticket(),
                entity.getNum_ticket(),
                entity.getTickets().getId(),
                entity.getElementos().getId(),
                null
        );
    }
}
