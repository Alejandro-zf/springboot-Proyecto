package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.UsuariosServices;
import com.proyecto.trabajo.dto.UsuariosCreateDto;
import com.proyecto.trabajo.dto.UsuariosDto;
import com.proyecto.trabajo.dto.UsuariosUpdateDto;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/Usuarios")
public class UsuariosController {

    private final UsuariosServices usuariosServices;

    public UsuariosController(UsuariosServices usuariosServices){
        this.usuariosServices= usuariosServices;
    }


    //Crear usuario
@PostMapping
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


//Obtener por ID
@GetMapping("/{id}")
public ResponseEntity<UsuariosDto>obtenerPorId(@PathVariable Long id) {
    UsuariosDto usuario = usuariosServices.buscarPorId(id);
    return ResponseEntity.ok(usuario);
}

//Listar a todos
@GetMapping
public ResponseEntity<List<UsuariosDto>> listarTodos() {
    List<UsuariosDto> usuarios = usuariosServices.listarTodos();
    return ResponseEntity.ok(usuarios);
}


//Actualizar al usuario
@PutMapping("/{id}")
public ResponseEntity<UsuariosDto> actualizar(@PathVariable Long id,
        @Valid @RequestBody UsuariosUpdateDto dto) {
        dto.setId_Usu(id);
    UsuariosDto actualizado = usuariosServices.actualizarUsuario(id, dto);
    return ResponseEntity.ok(actualizado);
} 



//Eliminar usuario por id
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Long id){
    usuariosServices.eliminar(id);
    return ResponseEntity.noContent().build();
}
    }
