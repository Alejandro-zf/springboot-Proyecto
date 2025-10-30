package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementoUpdateDtos;
import com.proyecto.trabajo.dto.ElementosCreateDto;

public interface ElementosServices {
    ElementoDto guardar(ElementosCreateDto dto);
    ElementoDto buscarPorId(Long id);
    List<ElementoDto> listarTodos();
    void eliminar(Long id);
    ElementoDto actualizarElemento(Long id, ElementoUpdateDtos dto);
    Map<String, Object> guardarMasivo(MultipartFile file);
    byte[] generarPlantilla();
}
