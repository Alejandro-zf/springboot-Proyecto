package com.proyecto.trabajo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.trabajo.Services.UsuariosImportService;
import com.proyecto.trabajo.Services.UsuariosServices;
import com.proyecto.trabajo.dto.UsuariosCreateDto;
import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.dto.UsuariosUpdateDto;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/Usuarios")
public class UsuariosController {

    private final UsuariosServices usuariosServices;
    private final UsuariosImportService usuariosImportService;

    
    public UsuariosController(UsuariosServices usuariosServices, UsuariosImportService usuariosImportService){
        this.usuariosServices = usuariosServices;
        this.usuariosImportService = usuariosImportService;
    }


    //Crear usuario - Acceso: Solo Admin (Tecnico e Instructor NO pueden)
@PostMapping
@PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> crear(@Valid @RequestBody UsuariosCreateDto dto) {
        try{
            UsuariosDto creado = usuariosServices.guardar(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("Mensaje", "Usuario creado exitosamente", "data", creado));
        } catch (IllegalStateException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", ex.getMessage()));
        }catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "Error al crear el usuario ", "detalle", ex.getMessage()));
        }
    }


//Obtener por ID - Acceso: Admin, Tecnico, Instructor
@GetMapping("/{id}")
@PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
public ResponseEntity<UsuariosDto>obtenerPorId(@PathVariable Long id) {
    UsuariosDto usuario = usuariosServices.buscarPorId(id);
    return ResponseEntity.ok(usuario);
}

//Listar a todos - Acceso: Admin, Tecnico, Instructor
@GetMapping
@PreAuthorize("hasAnyRole('Administrador', 'Tecnico', 'Instructor')")
public ResponseEntity<List<UsuariosDto>> listarTodos() {
    List<UsuariosDto> usuarios = usuariosServices.listarTodos();
    return ResponseEntity.ok(usuarios);
}


//Actualizar al usuario - Acceso: Solo Admin y Tecnico (Instructor NO puede)
@PutMapping("/{id}")
@PreAuthorize("hasAnyRole('Administrador', 'Tecnico')")
public ResponseEntity<UsuariosDto> actualizar(@PathVariable Long id,
        @Valid @RequestBody UsuariosUpdateDto dto) {
        dto.setId_Usu(id);
    UsuariosDto actualizado = usuariosServices.actualizarUsuario(id, dto);
    return ResponseEntity.ok(actualizado);
}


//Actualizar mi propio perfil - Acceso: Cualquier usuario autenticado
@PutMapping("/perfil/me")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<?> actualizarMiPerfil(@Valid @RequestBody UsuariosUpdateDto dto,
        org.springframework.security.core.Authentication authentication) {
    try {
        // Obtener el correo del usuario autenticado
        String correoAutenticado = authentication.getName();
        
        // Actualizar solo el perfil del usuario autenticado
        UsuariosDto actualizado = usuariosServices.actualizarMiPerfil(correoAutenticado, dto);
        return ResponseEntity.ok(actualizado);
    } catch (IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", ex.getMessage()));
    } catch (Exception ex) {
        ex.printStackTrace(); // Log del error en consola
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al actualizar el perfil", "detalle", ex.getMessage()));
    }
} 



//Eliminar usuario por id - Acceso: Solo Admin (Tecnico e Instructor NO pueden)
@DeleteMapping("/{id}")
@PreAuthorize("hasRole('Administrador')")
public ResponseEntity<Void> eliminar(@PathVariable Long id){
    usuariosServices.eliminar(id);
    return ResponseEntity.noContent().build();
}
    

    /**
     * Endpoint para subir un archivo Excel (.xlsx) con usuarios masivos.
     * Se delega el parsing a UsuariosImportService para mantener el controlador limpio.
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> uploadUsuariosMasivos(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Archivo vacío o no proporcionado"));
        }

        try (var is = file.getInputStream()) {
            var result = usuariosImportService.importFromExcel(is);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al procesar el archivo", "detalle", ex.getMessage()));
        }
    }

    // Descargar plantilla XLSX para usuarios (delegado al servicio)
    @GetMapping("/template")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<byte[]> descargarPlantillaUsuarios() {
        try {
            byte[] bytes = usuariosServices.generarPlantillaUsuarios();
            HttpHeaders headersResp = new HttpHeaders();
            headersResp.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headersResp.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usuarios_plantilla.xlsx");
            return new ResponseEntity<>(bytes, headersResp, HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Devuelve solamente la cabecera (nombres de columnas) que debe contener el XLSX
    @GetMapping("/template/headers")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<?> obtenerCabeceraPlantilla() {
        String[] headers = new String[] {"Nombre", "Apellido", "Correo", "Contraseña", "NúmeroDocumento", "IdTipoDocumento", "IdRole"};
        return ResponseEntity.ok(Map.of("headers", headers));
    }
}
