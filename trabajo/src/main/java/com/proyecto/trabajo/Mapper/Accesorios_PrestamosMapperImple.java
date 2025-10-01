package com.proyecto.trabajo.Mapper;

import org.springframework.stereotype.Component;

import com.proyecto.trabajo.dto.Accesorios_PrestamosDtos;
import com.proyecto.trabajo.models.Accesorios;
import com.proyecto.trabajo.models.Accesorios_Prestamos;
import com.proyecto.trabajo.models.Accesorios_Prestamosid;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.repository.AccesoriosRepository;
import com.proyecto.trabajo.repository.PrestamosRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class Accesorios_PrestamosMapperImple implements Accesorios_PrestamosMapper {

    private final PrestamosRepository prestamosRepository;
    private final AccesoriosRepository accesoriosRepository;

    public Accesorios_PrestamosMapperImple(PrestamosRepository prestamosRepository, AccesoriosRepository accesoriosRepository) {
        this.prestamosRepository = prestamosRepository;
        this.accesoriosRepository = accesoriosRepository;
    }

    @Override
    public Accesorios_Prestamos toEntity(Accesorios_PrestamosDtos dto) {
        Prestamos prestamos = prestamosRepository.findById(dto.getId_prest())
            .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

        Accesorios accesorio = accesoriosRepository.findById(dto.getId_acceso().intValue())
            .orElseThrow(() -> new EntityNotFoundException("Accesorio no encontrado"));

        Accesorios_Prestamosid id = new Accesorios_Prestamosid(
            dto.getId_acceso().intValue(),
            dto.getId_prest()
        );

        Accesorios_Prestamos ap = new Accesorios_Prestamos();
        ap.setId(id);
        ap.setPrestamos(prestamos);
        ap.setAccesorios(accesorio);
        return ap;
    }

    @Override
    public Accesorios_PrestamosDtos toDTO(Accesorios_Prestamos entity) {
        Accesorios_PrestamosDtos dto = new Accesorios_PrestamosDtos();
        dto.setId_prest(entity.getPrestamos().getId());
        if (entity.getAccesorios() != null) {
            dto.setId_acceso(entity.getAccesorios().getId().longValue());
            dto.setNom_acses(entity.getAccesorios().getNom_acce());
        }
        return dto;
    }
}
