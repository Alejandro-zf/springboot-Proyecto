package com.proyecto.trabajo.Services;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.trabajo.Mapper.UsuariosMapper;
import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.dto.UsuariosUpdateDto;
import com.proyecto.trabajo.models.Roles;
import com.proyecto.trabajo.models.Tip_documento;
import com.proyecto.trabajo.models.Roles_Usuario;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.RolesRepository;
import com.proyecto.trabajo.repository.Roles_UsuarioRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuariosServicesImple implements UsuariosServices {
    private final PasswordEncoder passwordEncoder;

    private final UsuariosRepository usuariosRepository;
    private final UsuariosMapper usuariosMapper;
    private final RolesRepository rolesRepository;
    private final Roles_UsuarioRepository rolesUsuarioRepository;
    private final com.proyecto.trabajo.repository.Tip_documentoRepository tipDocRepository;

    public UsuariosServicesImple(UsuariosRepository usuariosRepository,
    UsuariosMapper usuariosMapper,
    RolesRepository rolesRepository,
    Roles_UsuarioRepository rolesUsuarioRepository,
    PasswordEncoder passwordEncoder,
    com.proyecto.trabajo.repository.Tip_documentoRepository tipDocRepository) {
        this.usuariosRepository = usuariosRepository;
        this.usuariosMapper = usuariosMapper;
        this.rolesRepository = rolesRepository;
        this.rolesUsuarioRepository = rolesUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tipDocRepository = tipDocRepository;
    }

    @Override
    public byte[] generarPlantillaUsuarios() throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("usuarios_template");
            Row header = sheet.createRow(0);
            String[] headers = new String[] {"Nombre", "Apellido", "Correo", "Contraseña", "NúmeroDocumento", "IdTipoDocumento", "IdRole"};
            for (int i = 0; i < headers.length; i++) {
                Cell c = header.createCell(i);
                c.setCellValue(headers[i]);
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(bos);
            return bos.toByteArray();
        }
    }

    public UsuariosDto guardar(com.proyecto.trabajo.dto.UsuariosCreateDto dto) {
        Usuarios usuarios = usuariosMapper.toUsuariosFromCreateDto(dto);

        // Cifrar la contraseña antes de guardar
        if (usuarios.getPassword() != null && !usuarios.getPassword().isEmpty()) {
            usuarios.setPassword(passwordEncoder.encode(usuarios.getPassword()));
        }

        usuarios.setEstado((byte) 1);

        if (usuarios.getTip_documento() == null) {
            throw new IllegalArgumentException("El campo tip_document (tipo de documento) es obligatorio");
        }

        Usuarios guardado = usuariosRepository.save(usuarios);
        if (dto.getId_role() != null) {
            Roles rol = rolesRepository.findById(dto.getId_role())
                    .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
            Roles_Usuario ru = new Roles_Usuario();
            ru.setUsuario(guardado);
            ru.setRoles(rol);
            rolesUsuarioRepository.save(ru);
            guardado.getRole().add(ru);
        }
        return usuariosMapper.toUsuariosDto(guardado);
    }
    @Override
    public UsuariosDto buscarPorId(Long id) {
        return usuariosRepository.findById(id)
                .map(usuariosMapper::toUsuariosDto)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    @Override
    public List<UsuariosDto> listarTodos() {
        return usuariosRepository.findAll()
                .stream()
                .map(usuariosMapper::toUsuariosDto)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        usuariosRepository.deleteById(id);
    }

    @Override
    public UsuariosDto actualizarUsuario(Long id, com.proyecto.trabajo.dto.UsuariosUpdateDto dto) {
        Usuarios usuarios = usuariosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (dto.getNom_us() != null) {
            usuarios.setNom_usu(dto.getNom_us());
        }
        if (dto.getApe_us() != null) {
            usuarios.setApe_usu(dto.getApe_us());
        }
        if (dto.getCorre() != null) {
            usuarios.setCorreo(dto.getCorre());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuarios.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getEst_usu() != null) {
            Byte est = dto.getEst_usu();
            if (est < 1 || est > 2) {
                throw new IllegalArgumentException("Estado inválido. Debe ser 1 (activado) o 2 (desactivado)");
            }
            usuarios.setEstado(est);
        }

        
        if (dto.getId_rl() != null) {
            // Verificar si el rol ya es el mismo que tiene el usuario
            Long rolActualId = usuarios.getRole().isEmpty() ? null : usuarios.getRole().get(0).getRoles().getId();
            
            // Solo actualizar si el rol es diferente
            if (rolActualId == null || !rolActualId.equals(dto.getId_rl())) {
                // Eliminar roles existentes de la base de datos
                rolesUsuarioRepository.deleteAll(usuarios.getRole());
                usuarios.getRole().clear();
                
                // Agregar nuevo rol
                Roles rol = rolesRepository.findById(dto.getId_rl())
                        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
                Roles_Usuario ru = new Roles_Usuario();
                ru.setUsuario(usuarios);
                ru.setRoles(rol);
                usuarios.getRole().add(ru);
            }
        }

        Usuarios actualizado = usuariosRepository.save(usuarios);
        return usuariosMapper.toUsuariosDto(actualizado);
    }

    @Override
    @Transactional
    public UsuariosDto actualizarMiPerfil(String correoAutenticado, String contraseñaAutenticada, UsuariosUpdateDto dto) {
        // Buscar el usuario por su correo autenticado
        Usuarios usuarios = usuariosRepository.findByCorreo(correoAutenticado)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Actualizar solo los campos permitidos (sin cambiar el rol)
        if (dto.getNom_us() != null && !dto.getNom_us().isBlank()) {
            usuarios.setNom_usu(dto.getNom_us());
        }
        if (dto.getApe_us() != null && !dto.getApe_us().isBlank()) {
            usuarios.setApe_usu(dto.getApe_us());
        }

        // Actualizar correo solo si es diferente y no existe otro usuario con ese correo
        if (dto.getCorre() != null && !dto.getCorre().isBlank() 
                && !dto.getCorre().equalsIgnoreCase(correoAutenticado)) {
            if (usuariosRepository.findByCorreo(dto.getCorre()).isPresent()) {
                throw new IllegalStateException("El correo ya está registrado por otro usuario");
            }
            usuarios.setCorreo(dto.getCorre());
        }

        // Actualizar contraseña si se proporciona
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuarios.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // Actualizar número de documento si se proporciona
        if (dto.getNum_docu() != null) {
            usuarios.setNum_doc(dto.getNum_docu());
        }

        // Actualizar tipo de documento si se proporciona
        if (dto.getId_td() != null) {
            Tip_documento tipDoc = tipDocRepository.findById(dto.getId_td())
                    .orElseThrow(() -> new EntityNotFoundException("Tipo de documento no encontrado"));
            usuarios.setTip_documento(tipDoc);
        }

        // NO permitir cambiar el rol en este endpoint
        // Los usuarios no pueden cambiar su propio rol

        Usuarios actualizado = usuariosRepository.save(usuarios);
        return usuariosMapper.toUsuariosDto(actualizado);
    }
}