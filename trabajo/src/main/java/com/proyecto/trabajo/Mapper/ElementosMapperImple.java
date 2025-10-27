package com.proyecto.trabajo.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.ElementoDto;
import com.proyecto.trabajo.dto.ElementosCreateDto;
import com.proyecto.trabajo.dto.ElementoUpdateDtos;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Sub_categoria;
import com.proyecto.trabajo.repository.Sub_categoriaRepository;

import jakarta.persistence.EntityNotFoundException;


@Component
public class ElementosMapperImple implements ElementosMapper {

    private final Sub_categoriaRepository subCategoriaRepository;

    public ElementosMapperImple(Sub_categoriaRepository subCategoriaRepository) {
        this.subCategoriaRepository = subCategoriaRepository;
    }

    private static byte validarEstado(Byte estado) {
        if (estado == null) return 1; 
        if (estado < 0 || estado > 1) {
            throw new IllegalArgumentException("Estado de elemento inválido. Use 0 (no activo) o 1 (activo)");
        }
        return estado;
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
        elementos.setEstadosoelement(validarEstado(elementoDto.getEst_elemn()));

        if (elementoDto.getId_subcat() != null) {
            Sub_categoria subCategoria = subCategoriaRepository.findById(elementoDto.getId_subcat())
                .orElseThrow(() -> new EntityNotFoundException("Subcategoría no encontrada"));
            elementos.setSub_categoria(subCategoria);
        }

        return elementos;
    }

    @Override
    public ElementoDto toElementoDto(Elementos elementos) {
        if (elementos == null) {
            return null;
        }
        
        
        Byte estadoSincronizado = 1;
        if (elementos.getTickets() != null && !elementos.getTickets().isEmpty()) {
            for (var ticket : elementos.getTickets()) {
                if (ticket != null && ticket.getEstado_ticket() != null && ticket.getEstado_ticket().getId_estado() != null) {
                    byte estadoTicket = ticket.getEstado_ticket().getId_estado();
                    
                    if (estadoTicket == 1 || estadoTicket == 2) {
                        estadoSincronizado = 0;
                        break;
                    }
                }
            }
        }
        ElementoDto elementoDto = new ElementoDto();
        elementoDto.setId_elemen(elementos.getId());
        elementoDto.setNom_eleme(elementos.getNom_elemento());
        elementoDto.setObse(elementos.getObser());
        elementoDto.setNum_seri(elementos.getNum_serie());
        elementoDto.setComponen(elementos.getComponentes());
        elementoDto.setEst_elemn(estadoSincronizado);
        if (elementos.getSub_categoria() != null) {
            elementoDto.setId_categ(elementos.getSub_categoria().getCategoria().getId() != null ? elementos.getSub_categoria().getCategoria().getId().longValue() : null);
            elementoDto.setTip_catg(elementos.getSub_categoria().getCategoria().getNom_categoria());
            elementoDto.setId_subcat(elementos.getSub_categoria().getId());
            elementoDto.setSub_catg(elementos.getSub_categoria().getNom_subcategoria());
        }
        elementoDto.setMarc(elementos.getMarca());
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
        elementos.setEstadosoelement(validarEstado(createDto.getEst_elem()));
        elementos.setMarca(createDto.getMarc());
        if (createDto.getId_subcat() != null) {
            Sub_categoria subCategoria = subCategoriaRepository.findById(createDto.getId_subcat())
                .orElseThrow(() -> new EntityNotFoundException("Subcategoría no encontrada"));
            elementos.setSub_categoria(subCategoria);
        }
        return elementos;
    }

    @Override
    public void updateElementosFromUpdateDto(ElementoUpdateDtos updateDto, Elementos entity) {
        if (updateDto == null || entity == null) {
            return;
        }
        if (updateDto.getEst_elem() != null) {
            entity.setEstadosoelement(validarEstado(updateDto.getEst_elem()));
        }
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
