package com.proyecto.trabajo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.Services.AccesoriosServices;
import com.proyecto.trabajo.dto.AccesoriosCreateDtos;
import com.proyecto.trabajo.dto.AccesoriosDto;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/accesorios")

public class AccesoriosController {

    private final AccesoriosServices accesoriosServices;

    public AccesoriosController(AccesoriosServices accesoriosServices) {
        this.accesoriosServices = accesoriosServices;
    }   

    //crear accesorios 
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody AccesoriosCreateDtos dto) {
        try{
                AccesoriosDto creado = accesoriosServices.guardar(dto);
                return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of( "mensaje", "Accesorio creado exitosamente", "data", creado));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("errores1", ex.getMessage()));
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error","Error al crear el accesorio","detalle", ex.getMessage()));
        }
    }
     
    //obtener por id 
    @GetMapping("/{id}")
    public ResponseEntity<AccesoriosDto> buscarPorId (@PathVariable Integer id) {
        AccesoriosDto accesorio = accesoriosServices.buscarPorId(id);
        return ResponseEntity.ok(accesorio);
    }
    
    //Eliminar accesorios por id 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        accesoriosServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
