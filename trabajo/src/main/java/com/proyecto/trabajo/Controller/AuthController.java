package com.proyecto.trabajo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.trabajo.dto.ForgotPasswordRequest;
import com.proyecto.trabajo.dto.ResetPasswordRequest;
import com.proyecto.trabajo.Services.UsuariosServices;
import com.proyecto.trabajo.dto.JwtResponse;
import com.proyecto.trabajo.dto.UserInfoDto;
import com.proyecto.trabajo.dto.LoginRequest;
import com.proyecto.trabajo.security.JwtTokenUtil;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.models.Usuarios;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosServices usuariosServices;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales incorrectas", e);
        }

        // Buscar información completa del usuario antes de generar token
        Usuarios usuario = usuariosRepository.findByCorreo(loginRequest.getUsername()).orElse(null);
        
        // Verificar si el usuario está desactivado (estado 2)
        if (usuario != null && usuario.getEstado() == 2) {
            throw new Exception("Usuario desactivado. No tiene permiso para acceder a la plataforma.");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        // Extraer el primer rol del usuario
        String rol = null;
        if (usuario != null && usuario.getRole() != null && !usuario.getRole().isEmpty()) {
            rol = usuario.getRole().stream()
                    .findFirst()
                    .map(ru -> ru.getRoles().getNom_rol())
                    .orElse(null);
        }

        // Prefijar con "Bearer " para que el cliente lo use directamente en Authorization header
        final String bearerToken = "Bearer " + token;

        return ResponseEntity.ok(new JwtResponse(
            bearerToken,                    // token completo con "Bearer "
            usuario != null ? usuario.getId() : null,  // id del usuario
            loginRequest.getUsername(),     // username (correo)
            rol,                            // rol del usuario
            usuario != null ? usuario.getEstado() : 1  // estado del usuario
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(java.util.Collections.singletonMap("error", "No autenticado"));
        }

        String username = authentication.getName(); // correo
        Usuarios usuario = usuariosRepository.findByCorreo(username).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(404).body(java.util.Collections.singletonMap("error", "Usuario no encontrado"));
        }

        UserInfoDto dto = new UserInfoDto();
        dto.setId(usuario.getId());
        dto.setCorreo(usuario.getCorreo());
        dto.setNombre(usuario.getNom_usu());
        dto.setApellido(usuario.getApe_usu());
        dto.setRoles(
            usuario.getRole().stream()
                .map(ru -> ru.getRoles().getNom_rol().toUpperCase())
                .collect(Collectors.toList())
        );
        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para solicitar recuperación de contraseña
     * POST /auth/forgot-password
     * Body: { "email": "usuario@ejemplo.com" }
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            usuariosServices.requestPasswordReset(request.getEmail());
            return ResponseEntity.ok(java.util.Collections.singletonMap(
                "message", 
                "Si el correo existe, recibirás un enlace para recuperar tu contraseña"
            ));
        } catch (Exception e) {
            // Por seguridad, no revelamos si el correo existe o no
            return ResponseEntity.ok(java.util.Collections.singletonMap(
                "message", 
                "Si el correo existe, recibirás un enlace para recuperar tu contraseña"
            ));
        }
    }

    /**
     * Endpoint para restablecer la contraseña
     * POST /auth/reset-password
     * Body: { "token": "abc123", "newPassword": "nuevaContraseña" }
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            usuariosServices.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity.ok(java.util.Collections.singletonMap(
                "message", 
                "Contraseña actualizada exitosamente"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(java.util.Collections.singletonMap(
                "error", 
                e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(java.util.Collections.singletonMap(
                "error", 
                "Error al restablecer la contraseña"
            ));
        }
    }
}