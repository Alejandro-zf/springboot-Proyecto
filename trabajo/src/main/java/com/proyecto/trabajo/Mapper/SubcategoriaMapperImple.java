package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.SubcategoriaDtos;
import com.proyecto.trabajo.dto.Sub_categoriasCreateDtos;
import com.proyecto.trabajo.models.Sub_categoria;
import com.proyecto.trabajo.models.Categoria;
import com.proyecto.trabajo.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class SubcategoriaMapperImple implements SubcategoriaMapper {

    private final CategoriaRepository categoriaRepository;

    public SubcategoriaMapperImple(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Sub_categoria toSubcategoria(SubcategoriaDtos subcategoriaDto) {
        if (subcategoriaDto == null) {
            return null;
        }
        
        Sub_categoria subcategoria = new Sub_categoria();
        subcategoria.setId(subcategoriaDto.getId());
        subcategoria.setNom_subcategoria(subcategoriaDto.getNom_subcateg());
        
        if (subcategoriaDto.getId_cat() != null) {
            Categoria categoria = categoriaRepository.findById(subcategoriaDto.getId_cat().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + subcategoriaDto.getId_cat()));
            subcategoria.setCategoria(categoria);
        }
        
        return subcategoria;
    }

    @Override
    public SubcategoriaDtos toSubcategoriaDto(Sub_categoria subcategoria) {
        if (subcategoria == null) {
            return null;
        }
        
        SubcategoriaDtos subcategoriaDto = new SubcategoriaDtos();
        subcategoriaDto.setId(subcategoria.getId());
        subcategoriaDto.setNom_subcateg(subcategoria.getNom_subcategoria());
        
        if (subcategoria.getCategoria() != null) {
            subcategoriaDto.setId_cat(subcategoria.getCategoria().getId().longValue());
            subcategoriaDto.setNom_cat(subcategoria.getCategoria().getNom_categoria());
        }
        
        return subcategoriaDto;
    }

    @Override
    public Sub_categoria toSubcategoriaFromCreateDto(Sub_categoriasCreateDtos createDto) {
        if (createDto == null) {
            return null;
        }
        
        Sub_categoria subcategoria = new Sub_categoria();
        subcategoria.setNom_subcategoria(createDto.getNom_subcateg());
        
        if (createDto.getId_cat() != null) {
            Categoria categoria = categoriaRepository.findById(createDto.getId_cat().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + createDto.getId_cat()));
            subcategoria.setCategoria(categoria);
        }
        
        return subcategoria;
    }

    public List<SubcategoriaDtos> toSubcategoriaDtoList(List<Sub_categoria> subcategorias) {
        if (subcategorias == null) {
            return List.of();
        }
        
        List<SubcategoriaDtos> subcategoriaDtos = new ArrayList<>(subcategorias.size());
        for (Sub_categoria subcategoria : subcategorias) {
            subcategoriaDtos.add(toSubcategoriaDto(subcategoria));
        }
        
        return subcategoriaDtos;
    }
}
