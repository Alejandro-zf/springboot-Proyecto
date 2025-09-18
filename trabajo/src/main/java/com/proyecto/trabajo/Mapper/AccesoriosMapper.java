package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.AccesoriosDto;
import com.proyecto.trabajo.models.Accesorios;

public interface AccesoriosMapper {
    Accesorios toAccesorios(AccesoriosDto accesoriosDto);
    AccesoriosDto toAccesoriosDto(Accesorios accesorios);
}