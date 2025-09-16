package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.models.Prestamos;

public interface PrestamosMapper {
    Prestamos toEntity(PrestamosDto dto);
    PrestamosDto toDTO(Prestamos entity);
}
