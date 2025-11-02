package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.CategoriaCreateDtos;
import com.proyecto.trabajo.dto.CategoriaDtos;
import com.proyecto.trabajo.dto.CategoriaUpdateDtos;
import com.proyecto.trabajo.models.Categoria;

public interface CategoriaMapper {
    
    Categoria toCategoria(CategoriaDtos categoriaDto);
    
    CategoriaDtos toCategoriaDto(Categoria categoria);
    
    Categoria toCategoriaFromCreateDto(CategoriaCreateDtos createDto);
    
    void updateCategoriaFromUpdateDto(CategoriaUpdateDtos updateDto, Categoria categoria);
    
    List<CategoriaDtos> toCategoriaDtoList(List<Categoria> categorias);
}
