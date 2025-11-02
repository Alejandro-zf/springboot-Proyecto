package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.CategoriaCreateDtos;
import com.proyecto.trabajo.dto.CategoriaDtos;
import com.proyecto.trabajo.dto.CategoriaUpdateDtos;

public interface CategoriaServices {
    
    CategoriaDtos guardar(CategoriaCreateDtos dto);
    
    CategoriaDtos buscarPorId(Byte id);
    
    List<CategoriaDtos> listarTodos();
    
    CategoriaDtos actualizar(Byte id, CategoriaCreateDtos dto);
    
    CategoriaDtos actualizarCategoria(Byte id, CategoriaUpdateDtos dto);
    
    void eliminar(Byte id);
}
