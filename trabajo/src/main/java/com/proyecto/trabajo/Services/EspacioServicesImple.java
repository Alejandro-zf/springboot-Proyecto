package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.EspacioMapper;
import com.proyecto.trabajo.dto.EspacioCreateDto;
import com.proyecto.trabajo.dto.EspacioDto;
import com.proyecto.trabajo.dto.EspacioUpdateDto;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.repository.EspacioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EspacioServicesImple implements EspacioServices {

    private final EspacioRepository espacioRepository;
    private final EspacioMapper espacioMapper;

    public EspacioServicesImple(
        EspacioRepository espacioRepository,
        EspacioMapper espacioMapper
    ) {
        this.espacioRepository = espacioRepository;
        this.espacioMapper = espacioMapper;
    }

    private void validarDatosEspacio(String nom_espa, String descripcion) {
        if (nom_espa == null || nom_espa.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del espacio es obligatorio");
        }
        
        if (nom_espa.length() > 25) {
            throw new IllegalArgumentException("El nombre del espacio no puede exceder 25 caracteres");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        
        if (descripcion.length() > 900) {
            throw new IllegalArgumentException("La descripción no puede exceder 900 caracteres");
        }
    }

    @Override
    public EspacioDto guardar(EspacioCreateDto dto) {
        validarDatosEspacio(dto.getNom_espa(), dto.getDescripcion());
        
        // Si no se proporciona estadoespacio, por defecto es 1 (Activo)
        if (dto.getEstadoespacio() == null) {
            dto.setEstadoespacio((byte) 1);
        }
        
        Espacio entity = espacioMapper.toEspacioFromCreateDto(dto);
        Espacio guardado = espacioRepository.save(entity);
        return espacioMapper.toEspacioDto(guardado);
    }

    @Override
    public EspacioDto buscarPorId(Integer id) {
        return espacioRepository.findById(id)
                .map(espacioMapper::toEspacioDto)
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado con id: " + id));
    }

    @Override
    public List<EspacioDto> listarTodos() {
        List<Espacio> espacios = espacioRepository.findAll();
        return espacioMapper.toEspacioDtoList(espacios);
    }

    @Override
    public void eliminar(Integer id) {
        if (!espacioRepository.existsById(id)) {
            throw new EntityNotFoundException("Espacio no encontrado con id: " + id);
        }
        espacioRepository.deleteById(id);
    }

    @Override
    public EspacioDto actualizarEspacio(Integer id, EspacioUpdateDto dto) {
        Espacio entity = espacioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado con id: " + id));

        // Actualizar solo los campos que no son nulos
        if (dto.getNom_espa() != null && !dto.getNom_espa().trim().isEmpty()) {
            if (dto.getNom_espa().length() > 25) {
                throw new IllegalArgumentException("El nombre del espacio no puede exceder 25 caracteres");
            }
            entity.setNom_espa(dto.getNom_espa());
        }

        if (dto.getDescripcion() != null && !dto.getDescripcion().trim().isEmpty()) {
            if (dto.getDescripcion().length() > 900) {
                throw new IllegalArgumentException("La descripción no puede exceder 900 caracteres");
            }
            entity.setDescripcion(dto.getDescripcion());
        }

        if (dto.getEstadoespacio() != null) {
            entity.setEstadoespacio(dto.getEstadoespacio());
        }

        if (dto.getImagenes() != null) {
            entity.setImagenes(dto.getImagenes());
        }

        Espacio actualizado = espacioRepository.save(entity);
        return espacioMapper.toEspacioDto(actualizado);
    }
}
