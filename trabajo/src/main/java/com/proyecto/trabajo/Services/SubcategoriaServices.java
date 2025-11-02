package com.proyecto.trabajo.Services;

import java.util.List;

import com.proyecto.trabajo.dto.SubcategoriaDtos;
import com.proyecto.trabajo.dto.SubcategoriaUpdateDtos;
import com.proyecto.trabajo.dto.Sub_categoriasCreateDtos;

public interface SubcategoriaServices {
    SubcategoriaDtos guardar(Sub_categoriasCreateDtos dto);
    SubcategoriaDtos buscarPorId(Long id);
    List<SubcategoriaDtos> listarTodos();
    SubcategoriaDtos actualizar(Long id, Sub_categoriasCreateDtos dto);
    SubcategoriaDtos actualizarSubcategoria(Long id, SubcategoriaUpdateDtos dto);
    void eliminar(Long id);
    List<SubcategoriaDtos> listarPorCategoria(Long idCategoria);
}
