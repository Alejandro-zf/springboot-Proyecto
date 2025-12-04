package com.proyecto.trabajo.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.SolicitudesMapper;
import com.proyecto.trabajo.dto.SolicitudeCreateDto;
import com.proyecto.trabajo.dto.SolicitudesDto;
import com.proyecto.trabajo.dto.SolicitudesUpdateDtos;
import com.proyecto.trabajo.models.Elemento_Solicitudes;
import com.proyecto.trabajo.models.Elementos;
import com.proyecto.trabajo.models.Espacio;
import com.proyecto.trabajo.models.Prestamos;
import com.proyecto.trabajo.models.Prestamos_Elemento;
import com.proyecto.trabajo.models.Solicitudes;
import com.proyecto.trabajo.models.Sub_categoria; // Importar Entidad Sub_categoria
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.ElementosRepository;
import com.proyecto.trabajo.repository.EspacioRepository;
import com.proyecto.trabajo.repository.PrestamosElementoRepository;
import com.proyecto.trabajo.repository.PrestamosRepository;
import com.proyecto.trabajo.repository.SolicitudesRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.repository.Sub_categoriaRepository; // Repositorio de Subcategoría

import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitudesServicesImple implements SolicitudesServices {
    private static final Logger logger = LoggerFactory.getLogger(SolicitudesServicesImple.class);

    private final SolicitudesRepository solicitudesRepository;
    private final SolicitudesMapper solicitudesMapper;
    private final UsuariosRepository usuariosRepository;
    private final EspacioRepository espacioRepository;
    private final PrestamosRepository prestamosRepository;
    private final ElementosRepository elementosRepository;
    private final com.proyecto.trabajo.Mapper.PrestamosMapper prestamosMapper;
    private final PrestamosElementoRepository prestamosElementoRepository;
    private final Sub_categoriaRepository subCategoriaRepository;

    public SolicitudesServicesImple(SolicitudesRepository solicitudesRepository, SolicitudesMapper solicitudesMapper,
            UsuariosRepository usuariosRepository, EspacioRepository espacioRepository,
            PrestamosRepository prestamosRepository, ElementosRepository elementosRepository,
            com.proyecto.trabajo.Mapper.PrestamosMapper prestamosMapper,
            PrestamosElementoRepository prestamosElementoRepository,
            Sub_categoriaRepository subCategoriaRepository) {
        this.solicitudesRepository = solicitudesRepository;
        this.solicitudesMapper = solicitudesMapper;
        this.usuariosRepository = usuariosRepository;
        this.espacioRepository = espacioRepository;
        this.prestamosRepository = prestamosRepository;
        this.elementosRepository = elementosRepository;
        this.prestamosMapper = prestamosMapper;
        this.prestamosElementoRepository = prestamosElementoRepository;
        this.subCategoriaRepository = subCategoriaRepository;
    }

    @Transactional
    public void expirarSolicitudesVencidas() {
        List<Solicitudes> vencidas = solicitudesRepository.findVencidasNoExpiradas(LocalDateTime.now());
        if (vencidas == null || vencidas.isEmpty()) return;
        for (Solicitudes s : vencidas) {
            // Cambia el estado a Expirada (id=3) usando la relación
            if (s.getEstado_solicitudes() == null || s.getEstado_solicitudes().getId() != 3) {
                com.proyecto.trabajo.models.Estado_solicitudes estadoExpirada = new com.proyecto.trabajo.models.Estado_solicitudes();
                estadoExpirada.setId(3); // Debe existir en la BD
                s.setEstado_solicitudes(estadoExpirada);
                solicitudesRepository.save(s);
            }
            if (s.getElemento() != null) {
                for (Elemento_Solicitudes es : s.getElemento()) {
                    Elementos elem = es.getElementos();
                    if (elem != null) {
                        elem.setEstadosoelement((byte) 1);
                        elementosRepository.save(elem);
                    }
                }
            }
        }
    }

    private void sincronizarEstadoElementos(Solicitudes solicitud) {
        if (solicitud == null || solicitud.getElemento() == null) return;
        if (solicitud.getEstado_solicitudes() == null) return;
        // Si el estado es Aprobado (id=2), poner elementos en estado 2, si no, en 1
        final byte estadoFinal = (solicitud.getEstado_solicitudes().getId() == 2) ? (byte) 2 : (byte) 1;
        solicitud.getElemento().forEach(es -> {
            Elementos elem = es.getElementos();
            if (elem != null && (elem.getEstadosoelement() == null || elem.getEstadosoelement() != estadoFinal)) {
                elem.setEstadosoelement(estadoFinal);
                elementosRepository.save(elem);
            }
        });
    }

    @Override
    @Transactional
    public SolicitudesDto guardar(SolicitudeCreateDto dto, String username) {
        logger.info("[Solicitudes] Username autenticado recibido: {}", username);
        Usuarios usuario = usuariosRepository.findByCorreo(username).orElse(null);
        if (usuario == null) {
            logger.error("[Solicitudes] No se encontró usuario con correo: {}", username);
            throw new EntityNotFoundException("Usuario no encontrado para el usuario autenticado: " + username);
        } else {
            logger.info("[Solicitudes] Usuario encontrado: id={}, correo={}", usuario.getId(), usuario.getCorreo());
        }
        
        // 🚀 LÍNEA DE DIAGNÓSTICO: ¿Qué ID recibimos del DTO?
        logger.info("[DIAGNÓSTICO] ID Subcategoría recibido en DTO: {}", dto.getId_subcategoria());

        Solicitudes solicitudes = solicitudesMapper.toSolicitudesFromCreateDto(dto);
        
        // 🚀 CORRECCIÓN DEFINITIVA: Buscar y asignar la Entidad Sub_categoria
        if (dto.getId_subcategoria() != null) {
    // 1. Busca la Entidad Sub_categoria por el ID
    Sub_categoria subCategoria = subCategoriaRepository.findById(dto.getId_subcategoria())
        .orElseThrow(() -> new EntityNotFoundException("Subcategoría no encontrada con ID: " + dto.getId_subcategoria()));
    
    // 2. Asigna el objeto completo al campo 'sub_categoria' de la Solicitud
    solicitudes.setSub_categoria(subCategoria);
    
    // 💡 LOG SIMPLIFICADO: Solo usamos subCategoria.getId()
    // Esto evita el error de compilación "getNom_subcateg() is undefined"
    logger.info("[DIAGNÓSTICO] Subcategoría ID {} asignada correctamente a la Entidad.", subCategoria.getId());
    
} else {
     logger.warn("[DIAGNÓSTICO] El ID de subcategoría recibido es NULL. Revisar el frontend o el DTO.");
}

        solicitudes.setUsuario(usuario); // Asignación de Usuario

        if (dto.getIds_elem() != null && !dto.getIds_elem().isEmpty()
                && (solicitudes.getElemento() == null || solicitudes.getElemento().isEmpty())) {
            for (Long idElem : dto.getIds_elem()) {
                if (idElem == null) continue;
                Elementos elemento = elementosRepository.findById(idElem)
                    .orElseThrow(() -> new EntityNotFoundException("Elemento no encontrado"));
                Elemento_Solicitudes es = new Elemento_Solicitudes();
                es.setSolicitudes(solicitudes);
                es.setElementos(elemento);
                solicitudes.getElemento().add(es);
            }
        }
        
        if (solicitudes.getEspacio() == null && dto.getId_esp() != null) {
            Espacio espacio = espacioRepository.findById(dto.getId_esp().intValue())
                .orElseThrow(() -> new EntityNotFoundException("Espacio no encontrado"));
            solicitudes.setEspacio(espacio);
        }
        
        Solicitudes guardado = solicitudesRepository.save(solicitudes);
        Solicitudes solicitudFullPostSave = solicitudesRepository.findById(guardado.getId())
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada tras guardar"));
        expirarSolicitudesVencidas();
        sincronizarEstadoElementos(solicitudFullPostSave);

        if (guardado.getEstado_solicitudes() != null && guardado.getEstado_solicitudes().getId() == 2) {
            boolean sinPrestamo = guardado.getPrestamos() == null || guardado.getPrestamos().isEmpty();
            if (sinPrestamo) {
                Solicitudes solicitudFull = solicitudesRepository.findById(guardado.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada tras guardar"));
                if (solicitudFull.getUsuario() == null) {
                    throw new IllegalArgumentException("No se puede crear préstamo: usuario null en solicitud aprobada");
                }
                Prestamos p = prestamosMapper.fromSolicitudAprobada(solicitudFull);
                Prestamos prestamoGuardado = prestamosRepository.save(p);
                if (solicitudFull.getElemento() != null) {
                    for (Elemento_Solicitudes es : solicitudFull.getElemento()) {
                        if (es != null && es.getElementos() != null) {
                            Prestamos_Elemento pe = new Prestamos_Elemento();
                            pe.setPrestamos(prestamoGuardado);
                            pe.setElementos(es.getElementos());
                            pe.setObser_prest("AUTO");
                            prestamosElementoRepository.save(pe);
                        }
                    }
                }
            }
        }
        Solicitudes toReturn = solicitudesRepository.findById(solicitudFullPostSave.getId())
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada al preparar respuesta"));
        return solicitudesMapper.toSolicitudesDto(toReturn);
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudesDto buscarPorId(Long id) {
        expirarSolicitudesVencidas();
        Solicitudes solicitudes = solicitudesRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));
        return solicitudesMapper.toSolicitudesDto(solicitudes);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudesDto> listarTodos() {
        expirarSolicitudesVencidas();
        return solicitudesRepository.findAllWithDetails()
            .stream()
            .map(solicitudesMapper::toSolicitudesDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SolicitudesDto actualizarSolicitud(Long id, SolicitudesUpdateDtos dto) {
        Solicitudes solicitudes = solicitudesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        solicitudesMapper.updateSolicitudesFromUpdateDto(dto, solicitudes);
        
        Solicitudes actualizado = solicitudesRepository.save(solicitudes);
        sincronizarEstadoElementos(actualizado);

        expirarSolicitudesVencidas();

        // Verificar si se debe crear préstamo (estado 2 = Aprobado)
        boolean aprobado = dto != null && dto.getId_est_soli() != null && dto.getId_est_soli() == 2;
        boolean sinPrestamo = actualizado.getPrestamos() == null || actualizado.getPrestamos().isEmpty();
        
        System.out.println("DEBUG - actualizarSolicitud:");
        System.out.println("  - dto.getId_est_soli(): " + (dto != null ? dto.getId_est_soli() : "null"));
        System.out.println("  - actualizado.getEstado_solicitudes(): " + (actualizado.getEstado_solicitudes() != null ? actualizado.getEstado_solicitudes().getId() : "null"));
        System.out.println("  - aprobado: " + aprobado);
        System.out.println("  - sinPrestamo: " + sinPrestamo);
        System.out.println("  - tiene usuario: " + (actualizado.getUsuario() != null));
        System.out.println("  - tiene espacio: " + (actualizado.getEspacio() != null));

        if (aprobado && sinPrestamo) {
            if (actualizado.getUsuario() == null) {
                throw new IllegalArgumentException("No se puede crear el préstamo automáticamente porque la solicitud no tiene usuario asignado. Asigna un usuario antes de aprobar la solicitud.");
            }
            System.out.println("DEBUG - Creando préstamo automático...");
            System.out.println("  - Espacio ID: " + (actualizado.getEspacio() != null ? actualizado.getEspacio().getId() : "null"));
            System.out.println("  - Elementos en solicitud: " + (actualizado.getElemento() != null ? actualizado.getElemento().size() : 0));
            
            Prestamos p = new Prestamos();
            p.setFecha_entre(LocalDateTime.now());
            p.setTipo_prest("AUTO");
            p.setUsuario(actualizado.getUsuario());
            p.setEspacio(actualizado.getEspacio());
            p.setSolicitudes(actualizado);
            
            // Guardar el préstamo primero
            Prestamos prestamoGuardado = prestamosRepository.save(p);
            
            // Si la solicitud tiene elementos, crear Prestamos_Elemento para cada uno
            if (actualizado.getElemento() != null && !actualizado.getElemento().isEmpty()) {
                for (Elemento_Solicitudes es : actualizado.getElemento()) {
                    Prestamos_Elemento pe = new Prestamos_Elemento();
                    pe.setPrestamos(prestamoGuardado);
                    pe.setElementos(es.getElementos());
                    pe.setCantidad(1); // O usar la cantidad de la solicitud si existe
                    prestamoGuardado.getPrestamoss().add(pe);
                }
                prestamosRepository.save(prestamoGuardado);
            }
            
            System.out.println("DEBUG - Préstamo creado exitosamente con ID: " + prestamoGuardado.getId());
        }
        
        return solicitudesMapper.toSolicitudesDto(actualizado);
    }
}