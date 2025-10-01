package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementosCreateDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Categoria;
import com.proyecto.trabajo.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;


@Component
public class ElementosMapperImple implements ElementosMapper {

    private final CategoriaRepository categoriaRepository;

    public ElementosMapperImple(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Elementos toElementos(ElementoDto elementoDto) {
        if (elementoDto == null) {
            return null;
        }
        
        Elementos elementos = new Elementos();
        elementos.setId(elementoDto.getId_elemen());
        elementos.setNom_elemento(elementoDto.getNom_eleme());
        elementos.setObser(elementoDto.getObse());
        elementos.setNum_serie(elementoDto.getNum_seri());
        elementos.setComponentes(elementoDto.getComponen());

        if (elementoDto.getId_categ() != null) {
            Categoria categoria = categoriaRepository.findById(elementoDto.getId_categ().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
            elementos.setCategoria(categoria);
        }

        return elementos;
    }

    @Override
    public ElementoDto toElementoDto(Elementos elementos) {
        if (elementos == null) {
            return null;
        }
        
        ElementoDto elementoDto = new ElementoDto();
        elementoDto.setId_elemen(elementos.getId());
        elementoDto.setNom_eleme(elementos.getNom_elemento());
        elementoDto.setObse(elementos.getObser());
        elementoDto.setNum_seri(elementos.getNum_serie());
        elementoDto.setComponen(elementos.getComponentes());
        if (elementos.getCategoria() != null) {
            elementoDto.setId_categ(elementos.getCategoria().getId().longValue());
            elementoDto.setTip_catg(elementos.getCategoria().getNom_categoria());
        }
        
        return elementoDto;
    }

    @Override
    public Elementos toElementosFromCreateDto(ElementosCreateDto createDto) {
        if (createDto == null) {
            return null;
        }
        Elementos elementos = new Elementos();
        elementos.setNom_elemento(createDto.getNom_eleme());
        elementos.setObser(createDto.getObse());
        elementos.setNum_serie(createDto.getNum_seri());
        elementos.setComponentes(createDto.getComponen());
        if (createDto.getId_categoria() != null) {
            Categoria categoria = categoriaRepository.findById(createDto.getId_categoria().byteValue())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
            elementos.setCategoria(categoria);
        }
        return elementos;
    }

    public List<ElementoDto> toElementoDtoList(List<Elementos> elementos) {
        if (elementos == null) {
            return List.of();
        }
        List<ElementoDto> elementoDtos = new ArrayList<>(elementos.size());

        for (Elementos elemento : elementos) {
            elementoDtos.add(toElementoDto(elemento));
        }
        return elementoDtos;
        
    }
}
