package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.SubcategoriaMapper;
import com.proyecto.trabajo.dto.SubcategoriaDtos;
import com.proyecto.trabajo.dto.SubcategoriaUpdateDtos;
import com.proyecto.trabajo.dto.Sub_categoriasCreateDtos;
import com.proyecto.trabajo.models.Sub_categoria;
import com.proyecto.trabajo.repository.Sub_categoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SubcategoriaServicesImple implements SubcategoriaServices {

    private final Sub_categoriaRepository subcategoriaRepository;
    private final SubcategoriaMapper subcategoriaMapper;

    public SubcategoriaServicesImple(Sub_categoriaRepository subcategoriaRepository, 
                                     SubcategoriaMapper subcategoriaMapper) {
        this.subcategoriaRepository = subcategoriaRepository;
        this.subcategoriaMapper = subcategoriaMapper;
    }

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
        
    subcategoria.setNom_subcategoria(dto.getNom_subcateg());
        
        
        if (dto.getId_cat() != null) {
            Sub_categoria subcategoriaTemp = subcategoriaMapper.toSubcategoriaFromCreateDto(dto);
            subcategoria.setCategoria(subcategoriaTemp.getCategoria());
        }
        
        Sub_categoria actualizado = subcategoriaRepository.save(subcategoria);
        return subcategoriaMapper.toSubcategoriaDto(actualizado);
    }

    @Override
    public SubcategoriaDtos actualizarSubcategoria(Long id, SubcategoriaUpdateDtos dto) {
        // Buscar la subcategoría existente
        Sub_categoria subcategoria = subcategoriaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Subcategoría no encontrada con ID: " + id));
        
        // Validar nombre si se proporciona
        if (dto.getNom_subcategoria() != null && !dto.getNom_subcategoria().isBlank()) {
            validarNombreSubcategoria(dto.getNom_subcategoria());
        }
        
        // Validar estado si se proporciona
        if (dto.getEstado() != null && dto.getEstado() != 1 && dto.getEstado() != 2) {
            throw new IllegalArgumentException("El estado debe ser 1 (Activo) o 2 (Inactivo)");
        }
        
        // Actualizar campos usando el mapper
        subcategoriaMapper.updateSubcategoriaFromUpdateDto(dto, subcategoria);
        
        // Guardar y retornar
        Sub_categoria subcategoriaActualizada = subcategoriaRepository.save(subcategoria);
        return subcategoriaMapper.toSubcategoriaDto(subcategoriaActualizada);
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