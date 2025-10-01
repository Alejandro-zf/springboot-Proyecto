package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.ProblemasCreateDtos;
import com.proyecto.trabajo.dto.ProblemasDtos;

public interface ProblemasServices {
    ProblemasDtos guardar(ProblemasCreateDtos dto);
    ProblemasDtos buscarPorId(Byte id);
    List<ProblemasDtos> listarTodos();
    void eliminar(Byte id);
    ProblemasDtos actualizar(ProblemasDtos dto);
}
