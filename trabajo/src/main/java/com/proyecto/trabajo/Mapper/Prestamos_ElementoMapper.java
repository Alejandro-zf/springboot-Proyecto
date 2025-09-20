package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.Prestamos_ElementoDto;
import com.proyecto.trabajo.models.Prestamos_Elemento;

public interface Prestamos_ElementoMapper {

    Prestamos_Elemento toPrestamos_Elemento(Prestamos_ElementoDto dto);

    Prestamos_ElementoDto toDTO(Prestamos_Elemento prestamos_Elemento);

}
