 package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.Prestamos_ElementoDto;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.models.Prestamos_Elemento;
import com.proyecto.trabajo.models.Prestamos_Elementoid;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.PrestamosRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class Prestamos_ElementoMapperImple implements Prestamos_ElementoMapper {

    private final PrestamosRepository prestamosRepository;
    private final ElementosRepository elementosRepository;

    public Prestamos_ElementoMapperImple(PrestamosRepository prestamosRepository, ElementosRepository elementosRepository) {
        this.prestamosRepository = prestamosRepository;
        this.elementosRepository = elementosRepository;
    }

    @Override
        public Prestamos_Elemento toPrestamos_Elemento(Prestamos_ElementoDto dto) {
        Prestamos prestamos = prestamosRepository.findById(dto.getId_prest())
            .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

        Elementos elementos = elementosRepository.findById(dto.getId_element())
            .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));

        Prestamos_Elementoid id = new Prestamos_Elementoid(dto.getId_prest(), dto.getId_element());

        Prestamos_Elemento pe = new Prestamos_Elemento();
        pe.setId(id);
        pe.setPrestamos(prestamos);
        pe.setElementos(elementos);
        pe.setObser_prest(dto.getObs_pres());
        // Si hay campos tip_prst, nomb_ele en la entidad, agrégalos aquí

        return pe;
        }

    @Override
    public Prestamos_ElementoDto toDTO(Prestamos_Elemento entity) {
    return new Prestamos_ElementoDto(
        entity.getObser_prest(),
        entity.getPrestamos().getId(),
        null, // tip_prst no existe en la entidad
        entity.getElementos().getId(),
        null // nomb_ele no existe en la entidad
    );
    }
}
