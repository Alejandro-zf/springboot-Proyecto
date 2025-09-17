package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.UsuariosServices;
import com.proyecto.trabajo.dto.UsuariosDto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/Usuarios")
public class UsuariosController {

    private final UsuariosServices usuariosServices;

    public UsuariosController(UsuariosServices usuariosServices){
        this.usuariosServices= usuariosServices;
    }
/* 
    //Crear usuario
    @PostMapping
    public ResponseEntity <UsuariosDto> crear(@Valid @RequestBody UsuarioCreateDto dto ) {
        
        
        return entity;
    }
*/

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
/* 
@PutMapping("/{id}")
public ResponseEntity<UsuariosDto> actualizar(@PathVariable Long id, @Valid @RequestBody ) 
    
    
    return entity;

*/

//Eliminar empleado
@DeleteMapping("/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Long id){
    usuariosServices.eliminar(id);
    return ResponseEntity.noContent().build();
}
    }



