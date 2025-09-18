package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.AccesoriosMapper;
import com.proyecto.trabajo.dto.AccesoriosDto;
import com.proyecto.trabajo.models.Accesorios;
import com.proyecto.trabajo.repository.AccesoriosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AccesoriosServicesImple implements AccesoriosServices {

    private final AccesoriosRepository accesoriosRepository;
    private final AccesoriosMapper accesoriosMapper;

    public AccesoriosServicesImple(AccesoriosRepository accesoriosRepository, AccesoriosMapper accesoriosMapper) {
        this.accesoriosRepository = accesoriosRepository;
        this.accesoriosMapper = accesoriosMapper;
    }

    @Override
    public AccesoriosDto guardar(AccesoriosDto dto) {
        Accesorios entity = accesoriosMapper.toEntity(dto);
        Accesorios guardado = accesoriosRepository.save(entity);
        return accesoriosMapper.toDTO(guardado);
    }

    @Override
    public AccesoriosDto buscarPorId(Integer id) {
        return accesoriosRepository.findById(id)
                .map(accesoriosMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Accesorio no encontrado"));
    }

    @Override
    public List<AccesoriosDto> listarTodos() {
        return accesoriosRepository.findAll()
                .stream()
                .map(accesoriosMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        accesoriosRepository.deleteById(id);
    }
}