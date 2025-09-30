package com.proyecto.trabajo.Mapper;

import com.proyecto.trabajo.dto.Accesorios_PrestamosDtos;
import com.proyecto.trabajo.models.Accesorios_Prestamos;

public interface Accesorios_PrestamosMapper {

    Accesorios_Prestamos toEntity(Accesorios_PrestamosDtos dto);

    Accesorios_PrestamosDtos toDTO(Accesorios_Prestamos entity);
}
