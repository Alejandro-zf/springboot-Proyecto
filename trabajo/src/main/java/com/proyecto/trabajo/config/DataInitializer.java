package com.proyecto.trabajo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.proyecto.trabajo.models.Roles;
import com.proyecto.trabajo.models.Roles_Usuario;
import com.proyecto.trabajo.models.Roles_Usuarioid;
import com.proyecto.trabajo.models.Tip_documento;
import com.proyecto.trabajo.models.Usuarios;
import com.proyecto.trabajo.repository.RolesRepository;
import com.proyecto.trabajo.repository.Roles_UsuarioRepository;
import com.proyecto.trabajo.repository.Tip_documentoRepository;
import com.proyecto.trabajo.repository.UsuariosRepository;
import com.proyecto.trabajo.security.JwtTokenUtil;

import java.util.ArrayList;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private Roles_UsuarioRepository rolesUsuarioRepository;

    @Autowired
    private Tip_documentoRepository tipDocumentoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void run(String... args) throws Exception {
        initializeData();
    }

    private void initializeData() {
        System.out.println("🚀 Inicializando datos del sistema...");

        try {
            // 1. Obtener tipo de documento existente (debe existir previamente)
            Tip_documento tipoDoc = obtenerTipoDocumento();

            // 2. Obtener roles existentes (deben existir previamente)
            Roles rolInstructor = obtenerRol("Instructor");
            Roles rolAdministrador = obtenerRol("Administrador");
            Roles rolTecnico = obtenerRol("Tecnico");

            // 3. Crear usuarios con sus roles y tokens
            createUsuarioConRolYToken("instructor", "Instructor", "Sistema", "instructor@tech.com", 
                                       1234567890L, "instructor123", tipoDoc, rolInstructor);
            
            createUsuarioConRolYToken("administrador", "Administrador", "Sistema", "admin@tech.com", 
                                       9876543210L, "admin123", tipoDoc, rolAdministrador);
            
            createUsuarioConRolYToken("tecnico", "Tecnico", "Sistema", "tecnico@tech.com", 
                                       5555555555L, "tecnico123", tipoDoc, rolTecnico);

            System.out.println("✅ Inicialización de datos completada!");
        } catch (Exception e) {
            System.err.println("❌ Error en la inicialización: " + e.getMessage());
            System.err.println("   Asegúrate de que existan los roles (Instructor, Administrador, Tecnico) y al menos un tipo de documento en la base de datos.");
        }
    }

    private Tip_documento obtenerTipoDocumento() {
        // Obtener el primer tipo de documento disponible (debe existir previamente)
        Tip_documento tipoDoc = tipDocumentoRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe ningún tipo de documento en la base de datos"));
        
        System.out.println("📄 Usando tipo de documento: '" + tipoDoc.getTipo_doc() + "' (ID: " + tipoDoc.getId() + ")");
        return tipoDoc;
    }

    private Roles obtenerRol(String nombreRol) {
        // Buscar el rol que ya debe existir en la base de datos
        Roles rol = rolesRepository.findAll().stream()
                .filter(r -> r.getNom_rol().equalsIgnoreCase(nombreRol))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existe el rol '" + nombreRol + "' en la base de datos"));
        
        System.out.println("👤 Rol encontrado: '" + rol.getNom_rol() + "' (ID: " + rol.getId() + ")");
        return rol;
    }

    private void createUsuarioConRolYToken(String username, String nombre, String apellido, 
                                           String correo, Long numDoc, String password, 
                                           Tip_documento tipoDoc, Roles rol) {
        // Verificar si el usuario ya existe por correo
        Usuarios usuarioExistente = usuariosRepository.findAll().stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElse(null);

        if (usuarioExistente != null) {
            System.out.println("⚠️  Usuario '" + username + "' ya existe con ID: " + usuarioExistente.getId());
            generarYMostrarToken(username, password);
            return;
        }

        // Crear nuevo usuario - Usar el ID del tipo de documento que viene del parámetro
        Usuarios usuario = new Usuarios();
        usuario.setNom_usu(nombre);
        usuario.setApe_usu(apellido);
        usuario.setCorreo(correo);
        usuario.setNum_doc(numDoc);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setEstado((byte) 1);
        // Asignar el tipo de documento usando el objeto que ya tiene su ID
        usuario.setTip_documento(tipoDoc);
        usuario.setSolicitudes(new ArrayList<>());
        usuario.setTickets(new ArrayList<>());
        usuario.setPrestamos(new ArrayList<>());
        usuario.setRole(new ArrayList<>());
        usuario.setTrasabilidad(new ArrayList<>());

        // Guardar usuario
        usuario = usuariosRepository.save(usuario);
        System.out.println("✨ Usuario '" + username + "' creado exitosamente con ID: " + usuario.getId());
        System.out.println("   📋 Tipo documento ID: " + tipoDoc.getId());

        // Asignar rol al usuario usando los IDs de las entidades guardadas
        Roles_Usuario rolesUsuario = new Roles_Usuario();
        Roles_Usuarioid id = new Roles_Usuarioid(usuario.getId(), rol.getId());
        rolesUsuario.setId(id);
        rolesUsuario.setUsuario(usuario);
        rolesUsuario.setRoles(rol);
        rolesUsuarioRepository.save(rolesUsuario);
        System.out.println("   👤 Rol asignado: " + rol.getNom_rol() + " (ID: " + rol.getId() + ")");

        // Generar y mostrar token
        generarYMostrarToken(username, password);
    }

    private void generarYMostrarToken(String username, String password) {
        try {
            // Crear UserDetails para generar el token
            UserDetails userDetails = User.builder()
                    .username(username)
                    .password(password)
                    .authorities("ROLE_USER")
                    .build();

            String token = jwtTokenUtil.generateToken(userDetails);
            String bearerToken = "Bearer " + token;

            System.out.println("🔑 Token para '" + username + "':");
            System.out.println("   " + bearerToken);
            System.out.println("   Credenciales -> Usuario: " + username + " | Password: " + password);
            System.out.println();
        } catch (Exception e) {
            System.err.println("❌ Error generando token para " + username + ": " + e.getMessage());
        }
    }
}
