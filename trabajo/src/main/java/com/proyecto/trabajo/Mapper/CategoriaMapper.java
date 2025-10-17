package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.CategoriaCreateDtos;
import com.proyecto.trabajo.dto.CategoriaDtos;
import com.proyecto.trabajo.models.Categoria;

public interface CategoriaMapper {
    
    Categoria toCategoria(CategoriaDtos categoriaDto);
    
    CategoriaDtos toCategoriaDto(Categoria categoria);
    
    Categoria toCategoriaFromCreateDto(CategoriaCreateDtos createDto);
    
    List<CategoriaDtos> toCategoriaDtoList(List<Categoria> categorias);
}
