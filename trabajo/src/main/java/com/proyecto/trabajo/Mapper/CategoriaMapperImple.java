package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.CategoriaCreateDtos;
import com.proyecto.trabajo.dto.CategoriaDtos;
import com.proyecto.trabajo.models.Categoria;
import com.proyecto.trabajo.models.Sub_categoria;
import com.proyecto.trabajo.repository.Sub_categoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class CategoriaMapperImple implements CategoriaMapper {

    private final Sub_categoriaRepository subCategoriaRepository;

    public CategoriaMapperImple(Sub_categoriaRepository subCategoriaRepository) {
        this.subCategoriaRepository = subCategoriaRepository;
    }

    @Override
    public Categoria toCategoria(CategoriaDtos categoriaDto) {
        if (categoriaDto == null) {
            return null;
        }
        
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDto.getId_cat());
        categoria.setNom_categoria(categoriaDto.getNom_cat());
        
        return categoria;
    }

    @Override
    public CategoriaDtos toCategoriaDto(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        
        CategoriaDtos categoriaDto = new CategoriaDtos();
        categoriaDto.setId_cat(categoria.getId());
        categoriaDto.setNom_cat(categoria.getNom_categoria());
        
        // Si tiene subcategorías, tomar la primera para mostrar en el DTO
        if (categoria.getSub_categoria() != null && !categoria.getSub_categoria().isEmpty()) {
            Sub_categoria primeraSubcat = categoria.getSub_categoria().get(0);
            categoriaDto.setId_subcat(primeraSubcat.getId());
            categoriaDto.setNom_subcast(primeraSubcat.getNom_subcategoria());
        }
        
        return categoriaDto;
    }

    @Override
    public Categoria toCategoriaFromCreateDto(CategoriaCreateDtos createDto) {
        if (createDto == null) {
            return null;
        }
        
        Categoria categoria = new Categoria();
        categoria.setNom_categoria(createDto.getNom_cat());
        
        // Si se proporcionan IDs de subcategorías, cargarlas y asociarlas
        if (createDto.getId_subcat() != null && !createDto.getId_subcat().isEmpty()) {
            List<Sub_categoria> subcategorias = createDto.getId_subcat().stream()
                .map(id -> subCategoriaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Subcategoría no encontrada con id: " + id)))
                .collect(Collectors.toList());
            
            // Establecer la relación bidireccional
            for (Sub_categoria subcat : subcategorias) {
                subcat.setCategoria(categoria);
            }
            categoria.setSub_categoria(subcategorias);
        }
        
        return categoria;
    }

    @Override
    public List<CategoriaDtos> toCategoriaDtoList(List<Categoria> categorias) {
        if (categorias == null) {
            return List.of();
        }
        
        List<CategoriaDtos> categoriaDtos = new ArrayList<>(categorias.size());
        for (Categoria categoria : categorias) {
            categoriaDtos.add(toCategoriaDto(categoria));
        }
        
        return categoriaDtos;
    }
}
