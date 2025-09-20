package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.Tickets_elementoDto;
import com.proyecto.trabajo.models.Tickets_elemento;

public interface Tickets_elementoMapper {

    Tickets_elemento toTickets_elemento(Tickets_elementoDto dto);

    Tickets_elementoDto toTickets_elementoDto(Tickets_elemento tickets_elemento);

}
