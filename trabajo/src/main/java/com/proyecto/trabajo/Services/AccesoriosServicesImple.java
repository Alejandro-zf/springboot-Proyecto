package com.proyecto.trabajo.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.trabajo.Mapper.AccesoriosMapper;
import com.proyecto.trabajo.dto.AccesoriosDto;
import com.proyecto.trabajo.models.Accesorios;
import com.proyecto.trabajo.repository.AccesoriosRepository;

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
        return accesoriosMapper.toDTO(accesoriosRepository.save(entity));
    }

    @Override
    public AccesoriosDto buscarPorId(Integer id) {
        Accesorios entity = accesoriosRepository.findById(id).orElse(null);
        return accesoriosMapper.toDTO(entity);
    }

    @Override
    public List<AccesoriosDto> listarTodos() {
        List<Accesorios> entities = accesoriosRepository.findAll();
        return entities.stream().map(accesoriosMapper::toDTO).toList();
    }

    @Override
    public void eliminar(Integer id) {
        accesoriosRepository.deleteById(id);
    }
}
