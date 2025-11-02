package com.proyecto.trabajo.Mapper;

import java.util.List;

import com.proyecto.trabajo.dto.SubcategoriaDtos;
import com.proyecto.trabajo.dto.SubcategoriaUpdateDtos;
import com.proyecto.trabajo.dto.Sub_categoriasCreateDtos;
import com.proyecto.trabajo.models.Sub_categoria;

public interface SubcategoriaMapper {
    Sub_categoria toSubcategoria(SubcategoriaDtos subcategoriaDto);
    SubcategoriaDtos toSubcategoriaDto(Sub_categoria subcategoria);
    Sub_categoria toSubcategoriaFromCreateDto(Sub_categoriasCreateDtos createDto);
    List<SubcategoriaDtos> toSubcategoriaDtoList(List<Sub_categoria> subcategorias);
    void updateSubcategoriaFromUpdateDto(SubcategoriaUpdateDtos updateDto, Sub_categoria subcategoria);
}
