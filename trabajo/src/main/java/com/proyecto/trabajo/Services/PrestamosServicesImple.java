package com.proyecto.trabajo.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proyecto.trabajo.Mapper.PrestamosMapper;
import com.proyecto.trabajo.dto.PrestamosDto;
import com.proyecto.trabajo.dto.PrestamosCreateDto;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Prestamos_Elemento;
import com.proyecto.trabajo.repository.PrestamosRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.PrestamosElementoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PrestamosServicesImple implements PrestamosServices {

    private static final Logger logger = LoggerFactory.getLogger(PrestamosServicesImple.class);

    @Override
    @Transactional(readOnly = true)
    public List<PrestamosDto> listarActivos() {
        List<Prestamos> activos = prestamosRepository.findByEstado((byte)1);
        logger.info("📊 listarActivos() - Found {} active loans (estado=1)", activos.size());
        return activos.stream()
                .map(prestamosMapper::toPrestamosDto)
                .collect(Collectors.toList());
    }

    private final PrestamosRepository prestamosRepository;
    private final PrestamosMapper prestamosMapper;
    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;
    private final ElementosRepository elementosRepository;
    private final PrestamosElementoRepository prestamosElementoRepository;

    public PrestamosServicesImple(PrestamosRepository prestamosRepository, PrestamosMapper prestamosMapper,
            UsuariosRepository usuariosRepository, EspacioRepository espacioRepository,
            ElementosRepository elementosRepository,
            PrestamosElementoRepository prestamosElementoRepository) {
        this.prestamosRepository = prestamosRepository;
        this.prestamosMapper = prestamosMapper;
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.elementosRepository = elementosRepository;
        this.prestamosElementoRepository = prestamosElementoRepository;
    }

    @Override
    @Transactional
    public PrestamosDto guardar(PrestamosCreateDto dto) {

        if (dto.getIdUsuario() == null) {
            throw new IllegalArgumentException("idUsuario es obligatorio");
        }
        // idEsp es opcional - puede ser null si es préstamo de elemento sin espacio
        // if (dto.getIdEsp() == null) {
        //     throw new IllegalArgumentException("idEsp es obligatorio");
        // }
        
        try {
            System.out.println("DEBUG - Creando préstamo con datos: " + dto);
            Prestamos prestamos = prestamosMapper.toPrestamosFromCreateDto(dto);
            System.out.println("DEBUG - Préstamo mapeado: " + prestamos);
            Prestamos guardado = prestamosRepository.save(prestamos);
            System.out.println("DEBUG - Préstamo guardado con ID: " + guardado.getId());

            
            if (dto.getIdsElem() != null && !dto.getIdsElem().isEmpty()) {
                for (Long idElem : dto.getIdsElem()) {
                    if (idElem == null) continue;
                    System.out.println("DEBUG - Procesando elemento con ID: " + idElem);
                    Elementos elemento = elementosRepository.findById(idElem)
                        .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
                    Prestamos_Elemento pe = new Prestamos_Elemento();
                    pe.setPrestamos(guardado);
                    pe.setElementos(elemento);
                    pe.setObser_prest("AUTO");
                    prestamosElementoRepository.save(pe);
                    System.out.println("DEBUG - Prestamos_Elemento guardado");
                }
            }
            
            Prestamos full = prestamosRepository.findById(guardado.getId())
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado tras guardar"));
            return prestamosMapper.toPrestamosDto(full);
        } catch (Exception e) {
            System.out.println("ERROR - Excepción al guardar préstamo: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
    @Transactional(readOnly = true)
    public List<PrestamosDto> listarPorEstado(Integer estado) {
        return prestamosRepository.findByEstado(estado.byteValue())
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

    // Solo actualizar tipo si viene en el DTO
    if (dto.getTipo_pres() != null) {
        prestamos.setTipo_prest(dto.getTipo_pres());
    }
    
    // Solo actualizar fecha de entrega si viene en el DTO
    if (dto.getFecha_entreg() != null) {
        prestamos.setFecha_entre(dto.getFecha_entreg());
    }
    
    // Si se está marcando como finalizado (estado = 0) y no tiene fecha de recepción, asignar la actual
    if (dto.getEstado() != null && dto.getEstado() == 0 && prestamos.getFecha_recep() == null) {
        prestamos.setFecha_recep(java.time.LocalDateTime.now());
    } else if (dto.getFecha_repc() != null) {
        prestamos.setFecha_recep(dto.getFecha_repc());
    }

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
    
    // Actualizar el estado si viene en el DTO
    if (dto.getEstado() != null) {
        prestamos.setEstado(dto.getEstado());
    }

    Prestamos actualizado = prestamosRepository.save(prestamos);
    return prestamosMapper.toPrestamosDto(actualizado);
    }
}
