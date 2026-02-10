package com.proyecto.trabajo.Services;
import com.proyecto.trabajo.models.TicketProblema;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.TicketsMapper;
import com.proyecto.trabajo.dto.TicketsDtos;
import com.proyecto.trabajo.dto.TicketsCreateDto;
import com.proyecto.trabajo.models.Tickets;
import com.proyecto.trabajo.models.Problemas;
import com.proyecto.trabajo.models.Estado_ticket;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Trasabilidad;
import com.proyecto.trabajo.repository.TicketsRepository;
import com.proyecto.trabajo.repository.ProblemasRepository;
import com.proyecto.trabajo.repository.EstadoTicketRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.TrasabilidadRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketsServicesImple implements TicketsServices {

    private final TicketsRepository ticketsRepository;
    private final ProblemasRepository problemasRepository;
    private final TicketsMapper ticketsMapper;
    private final EstadoTicketRepository estadoTicketRepository;
    private final ElementosRepository elementosRepository;
    private final TrasabilidadRepository trasabilidadRepository;
    private final UsuariosRepository usuariosRepository;

    public TicketsServicesImple(TicketsRepository ticketsRepository, ProblemasRepository problemasRepository, TicketsMapper ticketsMapper, EstadoTicketRepository estadoTicketRepository, ElementosRepository elementosRepository, TrasabilidadRepository trasabilidadRepository, UsuariosRepository usuariosRepository) {
        this.ticketsRepository = ticketsRepository;
        this.problemasRepository = problemasRepository;
        this.ticketsMapper = ticketsMapper;
        this.estadoTicketRepository = estadoTicketRepository;
        this.elementosRepository = elementosRepository;
        this.trasabilidadRepository = trasabilidadRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    @Transactional
    public TicketsDtos guardar(TicketsCreateDto dto) {
        if (dto.getId_usu() == null) {
            throw new IllegalStateException("id_usu es obligatorio");
        }
        
        if (dto.getProblemas() == null || dto.getProblemas().isEmpty()) {
            throw new IllegalStateException("Al menos un problema es obligatorio");
        }
        if (dto.getAmbiente() == null || dto.getAmbiente().isBlank()) {
            throw new IllegalStateException("ambient es obligatorio");
        }
        if (dto.getFecha_in() == null) {
            dto.setFecha_in(LocalDateTime.now());
        }
        if (dto.getObser() == null) {
            dto.setObser("");
        }
        // El campo imageness es opcional, si es null se mantiene así
        if (dto.getImageness() == null) {
            dto.setImageness(null);
        }

        Tickets tickets = ticketsMapper.toTicketsFromCreateDto(dto);
        
        // Asignar usuario
        if (dto.getId_usu() != null) {
            tickets.setUsuario(usuariosRepository.findById(dto.getId_usu())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado")));
        }
        
        // Asegura que siempre se asigne un estado
        if (tickets.getIdEstTick() == null) {
            Estado_ticket estadoPendiente = estadoTicketRepository.findById((byte)2)
                .orElseThrow(() -> new EntityNotFoundException("Estado de ticket 'pendiente' (2) no encontrado"));
            tickets.setIdEstTick(estadoPendiente);
        }

        if (dto.getId_est_tick() != null) {
            Estado_ticket estado = estadoTicketRepository.findById(dto.getId_est_tick().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Estado de ticket no encontrado"));
            tickets.setIdEstTick(estado);
        } else if (tickets.getIdEstTick() == null) {
            Estado_ticket estadoPendiente = estadoTicketRepository.findById((byte)2)
                .orElseThrow(() -> new EntityNotFoundException("Estado de ticket 'pendiente' (2) no encontrado"));
            tickets.setIdEstTick(estadoPendiente);
        }

        if (dto.getProblemas() != null && !dto.getProblemas().isEmpty()) {
            System.out.println("🔧 Procesando " + dto.getProblemas().size() + " problemas...");
            for (var problemaDetalle : dto.getProblemas()) {
                Problemas problema = problemasRepository.findById(problemaDetalle.getId().byteValue())
                    .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado con ID: " + problemaDetalle.getId()));
                TicketProblema ticketProblema = new TicketProblema();
                ticketProblema.setTicket(tickets);
                ticketProblema.setProblema(problema);
                ticketProblema.setDescripcion(problemaDetalle.getDescripcion());
                if (problemaDetalle.getImagenes() != null && !problemaDetalle.getImagenes().isEmpty()) {
                    ticketProblema.setImagenes(String.join(",", problemaDetalle.getImagenes()));
                }
                tickets.getTicketProblemas().add(ticketProblema);
                System.out.println("✅ Agregado problema ID: " + problemaDetalle.getId() + " - " + problema.getDesc_problema());
            }
            System.out.println("📋 Total problemas en ticket: " + tickets.getTicketProblemas().size());
        }

        if (tickets.getObservaciones() == null) {
            tickets.setObservaciones(dto.getObser());
        }
        if (tickets.getElementos() == null && dto.getId_elem() != null) {
            Elementos elemento = elementosRepository.findById(dto.getId_elem())
                .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
            tickets.setElementos(elemento);
        }

        Tickets guardado = ticketsRepository.save(tickets);
        // Los TicketProblema se guardan en cascada
        
        try {
            Trasabilidad tr = new Trasabilidad();
            tr.setFecha(LocalDate.now());
            tr.setObservacion(null);
            tr.setUsuario(guardado.getUsuario());
            tr.setTickets(guardado);
            tr.setElementos(guardado.getElementos());
            trasabilidadRepository.save(tr);
        } catch (Exception ex) {
            // Log exception if necessary
        }

        System.out.println("🎫 TICKET CREADO - llamando sincronización...");
        sincronizarEstadoElementoPorTicket(guardado);
        return ticketsMapper.toTicketsDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketsDtos buscarPorId(Long id) {
        Tickets ticket = ticketsRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));
        return ticketsMapper.toTicketsDto(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarTodos() {
        return ticketsRepository.findAll().stream()
            .map(ticketsMapper::toTicketsDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarActivos() {
        // Estado ACTIVO = 1 (idEstTick -> idEstado)
        return ticketsRepository.findByIdEstTick_IdEstado((byte)1)
                .stream()
                .map(ticketsMapper::toTicketsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarPendientes() {
        // Estado PENDIENTE = 2 (idEstTick -> idEstado)
        return ticketsRepository.findByIdEstTick_IdEstado((byte)2)
                .stream()
                .map(ticketsMapper::toTicketsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketsDtos> listarFinalizados() {
        // Estados TERMINADO = 3 e INACTIVO = 4
        List<Tickets> terminados = ticketsRepository.findByIdEstTick_IdEstado((byte)3);
        List<Tickets> inactivos = ticketsRepository.findByIdEstTick_IdEstado((byte)4);
        
        List<Tickets> todos = new ArrayList<>();
        todos.addAll(terminados);
        todos.addAll(inactivos);
        
        return todos.stream()
                .map(ticketsMapper::toTicketsDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!ticketsRepository.existsById(id)) {
            throw new EntityNotFoundException("Ticket no encontrado");
        }
        ticketsRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TicketsDtos actualizar(Long id, com.proyecto.trabajo.dto.TicketsUpdateDtos dto) {
        Tickets tickets = ticketsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));

        // Solo actualiza los campos presentes en el DTO de actualización
        if (dto.getFecha_in() != null) {
            tickets.setFecha_ini(dto.getFecha_in());
        }
        if (dto.getFecha_fin() != null) {
            tickets.setFecha_finn(dto.getFecha_fin());
        }
        if (dto.getImageness() != null) {
            tickets.setImageness(dto.getImageness());
        }
    if (dto.getId_est_tick() != null) {
        Estado_ticket estado = estadoTicketRepository.findById(dto.getId_est_tick().byteValue())
            .orElseThrow(() -> new EntityNotFoundException("Estado de ticket no encontrado"));
        tickets.setIdEstTick(estado);
    }
    if (dto.getId_problem() != null) {
        Problemas problema = problemasRepository.findById(dto.getId_problem().byteValue())
            .orElseThrow(() -> new EntityNotFoundException("Problema no encontrado"));
        boolean exists = tickets.getTicketProblemas().stream()
            .anyMatch(tp -> tp.getProblema().getId().equals(problema.getId()));
        if (!exists) {
            TicketProblema ticketProblema = new TicketProblema();
            ticketProblema.setTicket(tickets);
            ticketProblema.setProblema(problema);
            tickets.getTicketProblemas().add(ticketProblema);
        }
    }

        Tickets actualizado = ticketsRepository.save(tickets);
        System.out.println("🔄 TICKET ACTUALIZADO - llamando sincronización...");
        sincronizarEstadoElementoPorTicket(actualizado);
        return ticketsMapper.toTicketsDto(actualizado);
    }
    @Override
    public com.proyecto.trabajo.models.Estado_ticket getEstadoTicketByNombre(String nombre) {
        if (nombre == null) return null;
        return estadoTicketRepository.findAll().stream()
            .filter(e -> e.getNom_estado() != null && e.getNom_estado().equalsIgnoreCase(nombre))
            .findFirst().orElse(null);
    }

    private void sincronizarEstadoElementoPorTicket(Tickets ticket) {
        if (ticket == null || ticket.getElementos() == null || ticket.getIdEstTick() == null) {
            System.out.println("⚠️ Sincronización saltada: ticket, elemento o estado nulos");
            return;
        }
        
        Elementos elemento = ticket.getElementos();
        Byte estadoTicket = ticket.getIdEstTick().getIdEstado();
        
        System.out.println(String.format("🔄 Sincronizando elemento: %s | Estado ticket actual: %d", 
            elemento.getNom_elemento(), estadoTicket));
        
        // Determinar el nuevo estado del elemento según el estado del ticket
        Byte nuevoEstadoElemento;
        switch (estadoTicket) {
            case 1: // Ticket Activo/En Proceso (TOMADO) -> Elemento en Mantenimiento
                nuevoEstadoElemento = 2; // Mantenimiento
                System.out.println("📝 Ticket TOMADO: Elemento debe pasar a MANTENIMIENTO (2)");
                break;
            case 3: // Ticket Terminado -> Elemento Activo
                nuevoEstadoElemento = 1; // Activo
                System.out.println("✅ Ticket TERMINADO: Elemento debe pasar a ACTIVO (1)");
                break;
            default: // Para otros estados (Pendiente, etc.) mantener estado actual
                System.out.println(String.format("ℹ️ Estado ticket %d no requiere cambio de elemento", estadoTicket));
                return;
        }
        
        // Solo actualizar si el estado cambió
        if (!nuevoEstadoElemento.equals(elemento.getEstadosoelement())) {
            Byte estadoAnterior = elemento.getEstadosoelement();
            elemento.setEstadosoelement(nuevoEstadoElemento);
            elementosRepository.save(elemento);
            
            String estadoTexto = nuevoEstadoElemento == 1 ? "Activo" : 
                               nuevoEstadoElemento == 2 ? "Mantenimiento" : "Inactivo";
            System.out.println(String.format("🔄 Elemento %s cambiado de estado %d → %d (%s)", 
                elemento.getNom_elemento(), estadoAnterior, nuevoEstadoElemento, estadoTexto));
        } else {
            System.out.println(String.format("ℹ️ Elemento %s ya está en estado %d - sin cambios", 
                elemento.getNom_elemento(), nuevoEstadoElemento));
        }
    }
}

