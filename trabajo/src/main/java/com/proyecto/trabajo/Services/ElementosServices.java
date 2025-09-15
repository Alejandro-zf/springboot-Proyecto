package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.ElementoDto;

public interface ElementosServices {
    public ElementoDto getElemento(Long id_elemen);

    public ElementoDto saveElemento(ElementoDto elementoDto);
 
    public List<ElementoDto> getAllElementos();

    public ElementoDto deleteElementos(Long id_elemen);
}
