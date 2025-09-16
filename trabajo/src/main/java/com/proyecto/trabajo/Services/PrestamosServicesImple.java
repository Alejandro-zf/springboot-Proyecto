package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.PrestamosMapper;
import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.repository.PrestamosRepository;

@Service
public class PrestamosServicesImple implements PrestamosServices {

    private final PrestamosRepository prestamosRepository;
    private final PrestamosMapper prestamosMapper;

    public PrestamosServicesImple(PrestamosRepository prestamosRepository, PrestamosMapper prestamosMapper) {
        this.prestamosRepository = prestamosRepository;
        this.prestamosMapper = prestamosMapper;
    }

    @Override
    public PrestamosDto guardar(PrestamosDto dto) {
        Prestamos entity = prestamosMapper.toEntity(dto);
        return prestamosMapper.toDTO(prestamosRepository.save(entity));
    }

    @Override
    public PrestamosDto buscarPorId(Long id) {
        Prestamos entity = prestamosRepository.findById(id).orElse(null);
        return prestamosMapper.toDTO(entity);
    }

    @Override
    public List<PrestamosDto> listarTodos() {
        List<Prestamos> entities = prestamosRepository.findAll();
        return entities.stream().map(prestamosMapper::toDTO).toList();
    }

    @Override
    public void eliminar(Long id) {
        prestamosRepository.deleteById(id);
    }
}
