package com.proyecto.trabajo.Services;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
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
    private final EmailService emailService;

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
    com.proyecto.trabajo.repository.Tip_documentoRepository tipDocRepository,
    EmailService emailService) {
        this.usuariosRepository = usuariosRepository;
        this.usuariosMapper = usuariosMapper;
        this.rolesRepository = rolesRepository;
        this.rolesUsuarioRepository = rolesUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.tipDocRepository = tipDocRepository;
        this.emailService = emailService;
    }

    @Override
    public byte[] generarPlantillaUsuarios() throws Exception {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            CreationHelper creationHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("usuarios_template");
            Row titleRow = sheet.createRow(0);
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Plantilla importación - Usuarios");
            org.apache.poi.ss.usermodel.CellStyle titleStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short)14);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
            titleStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            titleStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
            titleCell.setCellStyle(titleStyle);
            workbook.getSheet("usuarios_template").addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0,0,0,6));
            Row header = sheet.createRow(1);
            String[] headers = new String[] {"Nombre", "Apellido", "Correo", "Contraseña", "NúmeroDocumento", "IdTipoDocumento", "IdRole"};

            org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);

            for (int i = 0; i < headers.length; i++) {
                Cell c = header.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }
            Sheet listas = workbook.createSheet("dicce_list");
            Row lheader = listas.createRow(0);
            lheader.createCell(0).setCellValue("Id_TipoDocumento - Nombre");
            lheader.createCell(2).setCellValue("Id_Rol - Nombre");
            Row noteRow = listas.createRow(1);
            noteRow.createCell(0).setCellValue("NOTA: Ejemplos -> 1 = Cédula de Ciudadanía; 2 = Pasaporte; 3 = Cédula de Extranjería");

            int rowIdx = 2;
            try {
                var tipos = tipDocRepository.findAll();
                for (var td : tipos) {
                    Row r = listas.getRow(rowIdx) == null ? listas.createRow(rowIdx) : listas.getRow(rowIdx);
                    r.createCell(0).setCellValue(String.valueOf(td.getId()) + " - " + td.getTipo_doc());
                    rowIdx++;
                }
            } catch (Exception e) {
            }
            int rolesRow = 2;
            Row r0 = listas.getRow(rolesRow) == null ? listas.createRow(rolesRow) : listas.getRow(rolesRow);
            r0.createCell(2).setCellValue("1 - instructor (Predeterminado)");
            rolesRow++;
            try {
                var roles = rolesRepository.findAll();
                for (var rl : roles) {
                    if (rl.getId() != null && rl.getId().equals(1L)) continue;
                    Row r = listas.getRow(rolesRow) == null ? listas.createRow(rolesRow) : listas.getRow(rolesRow);
                    r.createCell(2).setCellValue(String.valueOf(rl.getId()) + " - " + rl.getNom_rol());
                    rolesRow++;
                }
            } catch (Exception e) {
            }
            int listasIndex = workbook.getSheetIndex(listas);
            workbook.setSheetHidden(listasIndex, true);

            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchorTipo = creationHelper.createClientAnchor();
            anchorTipo.setCol1(5);
            anchorTipo.setCol2(6);
            anchorTipo.setRow1(1);
            anchorTipo.setRow2(4);
            Comment commentTipo = drawing.createCellComment(anchorTipo);
            commentTipo.setString(creationHelper.createRichTextString("Seleccione el IdTipoDocumento (ver hoja oculta 'dicce_list' para la lista de ids y nombres). Ej: 1 = Cédula de Ciudadanía; 2 = Pasaporte; 3 = Cédula de Extranjería."));
            sheet.getRow(1).getCell(5).setCellComment(commentTipo);

            ClientAnchor anchorRole = creationHelper.createClientAnchor();
            anchorRole.setCol1(6);
            anchorRole.setCol2(7);
            anchorRole.setRow1(1);
            anchorRole.setRow2(4);
            Comment commentRole = drawing.createCellComment(anchorRole);
            commentRole.setString(creationHelper.createRichTextString("IdRole: coloque el id numérico del rol. Nota: 1 = instructor (Predeterminado) y debe mantenerse así para el flujo predeterminado. Revise 'dicce_list'."));
            sheet.getRow(1).getCell(6).setCellComment(commentRole);

            ClientAnchor anchorInstr = creationHelper.createClientAnchor();
            anchorInstr.setCol1(0);
            anchorInstr.setCol2(3);
            anchorInstr.setRow1(0);
            anchorInstr.setRow2(2);
            Comment commentInstr = drawing.createCellComment(anchorInstr);
            commentInstr.setString(creationHelper.createRichTextString("Esta plantilla importa usuarios. Rellene cada fila con los datos solicitados. Use la hoja oculta 'dicce_list' para ver los ids de Tipos de documento y Roles. No modifique la fila de encabezado."));
            sheet.getRow(0).getCell(0).setCellComment(commentInstr);
            int[] widths = new int[] {7000,7000,10000,7000,7000,6000,6000};
            for (int i = 0; i < headers.length; i++) {
                sheet.setColumnWidth(i, widths[i]);
            }
            sheet.createFreezePane(0,2);

            workbook.write(bos);
            return bos.toByteArray();
        }
    }

    public UsuariosDto guardar(com.proyecto.trabajo.dto.UsuariosCreateDto dto) {
        // ✅ VALIDACIÓN: No permitir crear usuarios con rol de Administrador
        if (dto.getId_role() != null) {
            Roles rol = rolesRepository.findById(dto.getId_role())
                    .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
            
            if ("Administrador".equalsIgnoreCase(rol.getNom_rol()) || dto.getId_role() == 2) {
                throw new IllegalStateException("No se pueden crear usuarios con rol de Administrador. Solo puede existir un administrador en el sistema.");
            }
        }
        
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
    public UsuariosDto actualizarMiPerfil(String correoAutenticado, UsuariosUpdateDto dto) {
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
            // Validar que se proporcionó la contraseña actual
            if (dto.getCurrentPassword() == null || dto.getCurrentPassword().isBlank()) {
                throw new IllegalArgumentException("Debes proporcionar tu contraseña actual para poder cambiarla");
            }
            
            // Verificar que la contraseña actual sea correcta
            if (!passwordEncoder.matches(dto.getCurrentPassword(), usuarios.getPassword())) {
                throw new IllegalArgumentException("La contraseña actual es incorrecta");
            }
            
            // Si todo es correcto, actualizar a la nueva contraseña
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

    /**
     * Solicita la recuperación de contraseña
     * - Genera un token único
     * - Establece la fecha de expiración (30 minutos)
     * - Envía el correo con el enlace
     */
    @Override
    @Transactional
    public void requestPasswordReset(String email) {
        // Buscar el usuario por correo
        Usuarios usuario = usuariosRepository.findByCorreo(email)
                .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con ese correo"));

        // Generar token único (UUID)
        String token = UUID.randomUUID().toString();

        // Establecer fecha de expiración (30 minutos desde ahora)
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);

        // Guardar token y fecha de expiración
        usuario.setResetToken(token);
        usuario.setResetTokenExpiry(expiry);
        usuariosRepository.save(usuario);

        // Enviar correo con el enlace
        emailService.sendPasswordResetEmail(email, token);
    }

    /**
     * Restablece la contraseña usando el token
     * - Valida que el token exista
     * - Valida que no esté expirado
     * - Actualiza la contraseña
     * - Elimina el token
     */
    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        // Buscar el usuario por el token
        Usuarios usuario = usuariosRepository.findByResetToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido o expirado"));

        // Validar que el token no haya expirado
        if (usuario.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El token ha expirado. Solicita uno nuevo.");
        }

        // Actualizar la contraseña (encriptada)
        usuario.setPassword(passwordEncoder.encode(newPassword));

        // Limpiar el token y la fecha de expiración
        usuario.setResetToken(null);
        usuario.setResetTokenExpiry(null);

        usuariosRepository.save(usuario);
    }
}