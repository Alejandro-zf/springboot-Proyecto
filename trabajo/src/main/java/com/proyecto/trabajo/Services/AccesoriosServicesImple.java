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
        Accesorios entity = accesoriosMapper.toAccesorios(dto);
        Accesorios guardado = accesoriosRepository.save(entity);
        return accesoriosMapper.toAccesoriosDto(guardado);
    }

    @Override
    public AccesoriosDto buscarPorId(Integer id) {
        return accesoriosRepository.findById(id)
                .map(accesoriosMapper::toAccesoriosDto)
                .orElseThrow(() -> new EntityNotFoundException("Accesorio no encontrado"));
    }

    @Override
    public List<AccesoriosDto> listarTodos() {
        return accesoriosRepository.findAll()
                .stream()
                .map(accesoriosMapper::toAccesoriosDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        accesoriosRepository.deleteById(id);
    }

    @Override
    public AccesoriosDto actualizarAccesorio(AccesoriosDto dto) {
        Accesorios accesorios = accesoriosMapper.toAccesorios(dto);
        Accesorios actualizado = accesoriosRepository.save(accesorios);
        return accesoriosMapper.toAccesoriosDto(actualizado);
    }
}
