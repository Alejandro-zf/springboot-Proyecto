package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.SubcategoriaMapper;
import com.proyecto.trabajo.dto.SubcategoriaDtos;
import com.proyecto.trabajo.dto.Sub_categoriasCreateDtos;
import com.proyecto.trabajo.models.Sub_categoria;
import com.proyecto.trabajo.repository.Sub_categoriaRepository;
import com.proyecto.trabajo.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubcategoriaServicesImple implements SubcategoriaServices {

    private final Sub_categoriaRepository subcategoriaRepository;
    private final SubcategoriaMapper subcategoriaMapper;
    private final CategoriaRepository categoriaRepository;

    public SubcategoriaServicesImple(Sub_categoriaRepository subcategoriaRepository, 
                                     SubcategoriaMapper subcategoriaMapper,
                                     CategoriaRepository categoriaRepository) {
        this.subcategoriaRepository = subcategoriaRepository;
        this.subcategoriaMapper = subcategoriaMapper;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Valida el nombre de la subcategoría
     * @param nombre El nombre a validar
     * @throws IllegalArgumentException si el nombre es inválido
     */
    private void validarNombreSubcategoria(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la subcategoría es obligatorio");
        }
        
        if (nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre de la subcategoría no puede exceder 50 caracteres");
        }
    }

    @Override
    public SubcategoriaDtos guardar(Sub_categoriasCreateDtos dto) {
        validarNombreSubcategoria(dto.getNom_subcateg());
        if (dto.getId_cat() == null || !categoriaRepository.existsById(dto.getId_cat().byteValue())) {
            throw new IllegalArgumentException("La categoría asociada no existe");
        }
        Sub_categoria subcategoria = subcategoriaMapper.toSubcategoriaFromCreateDto(dto);
        Sub_categoria guardado = subcategoriaRepository.save(subcategoria);
        return subcategoriaMapper.toSubcategoriaDto(guardado);
    }

    @Override
    public SubcategoriaDtos buscarPorId(Long id) {
        return subcategoriaRepository.findById(id)
                .map(subcategoriaMapper::toSubcategoriaDto)
                .orElseThrow(() -> new EntityNotFoundException("Subcategoría no encontrada con id: " + id));
    }

    @Override
    public List<SubcategoriaDtos> listarTodos() {
        List<Sub_categoria> subcategorias = subcategoriaRepository.findAll();
        return subcategoriaMapper.toSubcategoriaDtoList(subcategorias);
    }

    @Override
    public SubcategoriaDtos actualizar(Long id, Sub_categoriasCreateDtos dto) {
        validarNombreSubcategoria(dto.getNom_subcateg());
        
        Sub_categoria subcategoria = subcategoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategoría no encontrada con id: " + id));
        
        // Actualizar el nombre
        subcategoria.setNom_subcategoria(dto.getNom_subcateg());
        
        // Actualizar la categoría si se proporciona
        if (dto.getId_cat() != null) {
            Sub_categoria subcategoriaTemp = subcategoriaMapper.toSubcategoriaFromCreateDto(dto);
            subcategoria.setCategoria(subcategoriaTemp.getCategoria());
        }
        
        Sub_categoria actualizado = subcategoriaRepository.save(subcategoria);
        return subcategoriaMapper.toSubcategoriaDto(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Sub_categoria subcategoria = subcategoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subcategoría no encontrada con id: " + id));
        
        if (subcategoria.getElementos() != null && !subcategoria.getElementos().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar la subcategoría porque tiene elementos asociados");
        }
        
        subcategoriaRepository.delete(subcategoria);
    }

    @Override
    public List<SubcategoriaDtos> listarPorCategoria(Long idCategoria) {
        List<Sub_categoria> subcategorias = subcategoriaRepository.findAll();
        return subcategorias.stream()
                .filter(sub -> sub.getCategoria() != null && 
                              sub.getCategoria().getId().longValue() == idCategoria)
                .map(subcategoriaMapper::toSubcategoriaDto)
                .toList();
    }
}
