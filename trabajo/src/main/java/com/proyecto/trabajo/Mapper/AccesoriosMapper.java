package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.AccesoriosDto;
import com.proyecto.trabajo.models.Accesorios;

public interface AccesoriosMapper {
    Accesorios toEntity(AccesoriosDto dto);
    AccesoriosDto toDTO(Accesorios entity);
}