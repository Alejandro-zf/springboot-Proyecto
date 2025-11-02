package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.CategoriaMapper;
import com.proyecto.trabajo.dto.CategoriaCreateDtos;
import com.proyecto.trabajo.dto.CategoriaDtos;
import com.proyecto.trabajo.dto.CategoriaUpdateDtos;
import com.proyecto.trabajo.models.Categoria;
import com.proyecto.trabajo.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaServicesImple implements CategoriaServices {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaServicesImple(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    private void validarNombreCategoria(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }
        
        if (nombre.length() > 30) {
            throw new IllegalArgumentException("El nombre de la categoría no puede exceder 30 caracteres");
        }
    }

    @Override
    public CategoriaDtos guardar(CategoriaCreateDtos dto) {
        validarNombreCategoria(dto.getNom_cat());
        
        Categoria categoria = categoriaMapper.toCategoriaFromCreateDto(dto);
        Categoria guardado = categoriaRepository.save(categoria);
        return categoriaMapper.toCategoriaDto(guardado);
    }

    @Override
    public CategoriaDtos buscarPorId(Byte id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toCategoriaDto)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));
    }

    @Override
    public List<CategoriaDtos> listarTodos() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categoriaMapper.toCategoriaDtoList(categorias);
    }

    @Override
    public CategoriaDtos actualizar(Byte id, CategoriaCreateDtos dto) {
        validarNombreCategoria(dto.getNom_cat());
        
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));
        
    categoria.setNom_categoria(dto.getNom_cat());
        
        Categoria actualizado = categoriaRepository.save(categoria);
        return categoriaMapper.toCategoriaDto(actualizado);
    }

    @Override
    public CategoriaDtos actualizarCategoria(Byte id, CategoriaUpdateDtos dto) {
        // Buscar la categoría existente
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));
        
        // Validar nombre si se proporciona
        if (dto.getNom_categoria() != null && !dto.getNom_categoria().isBlank()) {
            validarNombreCategoria(dto.getNom_categoria());
        }
        
        // Validar estado si se proporciona
        if (dto.getEstado() != null && dto.getEstado() != 1 && dto.getEstado() != 2) {
            throw new IllegalArgumentException("El estado debe ser 1 (Activo) o 2 (Inactivo)");
        }
        
        // Actualizar campos usando el mapper
        categoriaMapper.updateCategoriaFromUpdateDto(dto, categoria);
        
        // Guardar y retornar
        Categoria categoriaActualizada = categoriaRepository.save(categoria);
        return categoriaMapper.toCategoriaDto(categoriaActualizada);
    }

    @Override
    public void eliminar(Byte id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoría no encontrada con id: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
