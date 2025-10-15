package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.PrestamosMapper;
import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.dto.PrestamosCreateDto;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Prestamos_Elemento;
import com.proyecto.trabajo.models.Accesorios;
import com.proyecto.trabajo.models.Accesorios_Prestamos;
import com.proyecto.trabajo.repository.PrestamosRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.AccesoriosRepository;
import com.proyecto.trabajo.repository.PrestamosElementoRepository;
import com.proyecto.trabajo.repository.Accesorios_PrestamosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PrestamosServicesImple implements PrestamosServices {

    private final PrestamosRepository prestamosRepository;
    private final PrestamosMapper prestamosMapper;
    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;
    private final ElementosRepository elementosRepository;
    private final AccesoriosRepository accesoriosRepository;
    private final PrestamosElementoRepository prestamosElementoRepository;
    private final Accesorios_PrestamosRepository accesoriosPrestamosRepository;

    public PrestamosServicesImple(PrestamosRepository prestamosRepository, PrestamosMapper prestamosMapper,
            UsuariosRepository usuariosRepository, EspacioRepository espacioRepository,
            ElementosRepository elementosRepository, AccesoriosRepository accesoriosRepository,
            PrestamosElementoRepository prestamosElementoRepository, Accesorios_PrestamosRepository accesoriosPrestamosRepository) {
        this.prestamosRepository = prestamosRepository;
        this.prestamosMapper = prestamosMapper;
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.elementosRepository = elementosRepository;
        this.accesoriosRepository = accesoriosRepository;
        this.prestamosElementoRepository = prestamosElementoRepository;
        this.accesoriosPrestamosRepository = accesoriosPrestamosRepository;
    }

    @Override
    @Transactional
    public PrestamosDto guardar(PrestamosCreateDto dto) {
        if (dto.getId_usuario() == null) {
            throw new IllegalArgumentException("id_usuario es obligatorio");
        }
        if (dto.getId_esp() == null) {
            throw new IllegalArgumentException("id_esp es obligatorio");
        }
        Prestamos prestamos = prestamosMapper.toPrestamosFromCreateDto(dto);
        Prestamos guardado = prestamosRepository.save(prestamos);

        // Asociar elementos si vienen en el DTO
        if (dto.getIds_elem() != null && !dto.getIds_elem().isEmpty()) {
            for (Long idElem : dto.getIds_elem()) {
                if (idElem == null) continue;
                Elementos elemento = elementosRepository.findById(idElem)
                    .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
                Prestamos_Elemento pe = new Prestamos_Elemento();
                pe.setPrestamos(guardado);
                pe.setElementos(elemento);
                pe.setObser_prest("AUTO");
                prestamosElementoRepository.save(pe);
            }
        }
        // Asociar accesorios si vienen en el DTO
        if (dto.getIds_acces() != null && !dto.getIds_acces().isEmpty()) {
            for (Long idAcc : dto.getIds_acces()) {
                if (idAcc == null) continue;
                Accesorios accesorio = accesoriosRepository.findById(idAcc.intValue())
                    .orElseThrow(() -> new EntityNotFoundException("Accesorio no encontrado"));
                Accesorios_Prestamos ap = new Accesorios_Prestamos();
                ap.setPrestamos(guardado);
                ap.setAccesorios(accesorio);
                accesoriosPrestamosRepository.save(ap);
            }
        }

        // Recargar para asegurar relaciones presentes y evitar nulls al mapear
        Prestamos full = prestamosRepository.findById(guardado.getId())
            .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado tras guardar"));
        return prestamosMapper.toPrestamosDto(full);
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamosDto buscarPorId(Long id) {
        Prestamos prestamos = prestamosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        return prestamosMapper.toPrestamosDto(prestamos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamosDto> listarTodos() {
        return prestamosRepository.findAll()
                .stream()
                .map(prestamosMapper::toPrestamosDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Prestamos prestamos = prestamosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));
        prestamosRepository.delete(prestamos);
    }

    @Override
    @Transactional
    public PrestamosDto actualizarPrestamo(PrestamosDto dto) {
    Prestamos prestamos = prestamosRepository.findById(dto.getId_prest())
        .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado"));

    prestamos.setTipo_prest(dto.getTipo_pres());
    prestamos.setFecha_entre(dto.getFecha_entreg());
    prestamos.setFecha_recep(dto.getFecha_repc());

    if (dto.getId_usu() != null) {
        Usuarios usuario = usuariosRepository.findById(dto.getId_usu())
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        prestamos.setUsuario(usuario);
    }
    if (dto.getId_espac() != null) {
        Espacio espacio = espacioRepository.findById(dto.getId_espac().intValue())
            .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
        prestamos.setEspacio(espacio);
    }

    Prestamos actualizado = prestamosRepository.save(prestamos);
    return prestamosMapper.toPrestamosDto(actualizado);
    }
}
