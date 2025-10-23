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
        pe.setCantidad(dto.getCant_elem());
        // Si viene id_solicitud, asociar la solicitud (requiere SolicitudesRepository si se quiere buscar la entidad)
        // Si viene solicitudAceptada, se puede usar para lógica adicional
        return pe;
        }

    @Override
    public Prestamos_ElementoDto toDTO(Prestamos_Elemento entity) {
        Prestamos_ElementoDto dto = new Prestamos_ElementoDto();
        dto.setObs_pres(entity.getObser_prest());
        dto.setId_prest(entity.getPrestamos().getId());
        dto.setId_element(entity.getElementos().getId());
        dto.setCant_elem(entity.getCantidad());
        // Mostrar solicitud si existe
        // Si la entidad tiene referencia a solicitud, se puede mostrar
        // if (entity.getSolicitud() != null) {
        //     dto.setId_solicitud(entity.getSolicitud().getId());
        //     dto.setSolicitudAceptada(entity.getSolicitud().getEstadosolicitud() != null && entity.getSolicitud().getEstadosolicitud() == 1);
        // }
        return dto;
    }
}
