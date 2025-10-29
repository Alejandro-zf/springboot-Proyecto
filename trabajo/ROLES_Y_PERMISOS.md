# 🔐 SISTEMA DE ROLES Y PERMISOS - TECH INVENTORY

## 📋 RESUMEN EJECUTIVO

El sistema ha sido configurado con **seguridad a nivel de método** usando `@PreAuthorize` en cada endpoint. Esto garantiza control granular sobre las operaciones CRUD por rol.

---

## 👥 ROLES DEL SISTEMA

| ID | Rol | Descripción |
|----|-----|-------------|
| 1 | **Administrador** | Acceso completo (CRUD + DELETE) |
| 2 | **Instructor** | Solo crear y consultar (CREATE + READ) |
| 3 | **Tecnico** | Crear, consultar y modificar (CREATE + READ + UPDATE) |

---

## 🎯 MATRIZ DE PERMISOS POR OPERACIÓN

### ✅ Resumen Visual

| Operación | Admin | Tecnico | Instructor |
|-----------|:-----:|:-------:|:----------:|
| **POST** (Crear) | ✅ | ✅ | ✅ |
| **GET** (Consultar) | ✅ | ✅ | ✅ |
| **PUT** (Modificar) | ✅ | ✅ | ❌ |
| **DELETE** (Eliminar) | ✅ | ❌ | ❌ |

---

## 📂 CONTROLADORES CONFIGURADOS

### 1. **UsuariosController** (`/api/Usuarios`)

| Endpoint | Método | Admin | Tecnico | Instructor |
|----------|--------|-------|---------|------------|
| `POST /api/Usuarios` | Crear usuario | ✅ | ✅ | ✅ |
| `GET /api/Usuarios/{id}` | Consultar por ID | ✅ | ✅ | ✅ |
| `GET /api/Usuarios` | Listar todos | ✅ | ✅ | ✅ |
| `PUT /api/Usuarios/{id}` | Actualizar | ✅ | ✅ | ❌ |
| `DELETE /api/Usuarios/{id}` | Eliminar | ✅ | ❌ | ❌ |

**Anotaciones aplicadas:**
```java
@PostMapping
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")

@GetMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")

@GetMapping
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO', 'INSTRUCTOR')")

@PutMapping("/{id}")
@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'TECNICO')")

@DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMINISTRADOR')")
```

---

### 2. **TicketsController** (`/api/tickets`)

| Endpoint | Método | Admin | Tecnico | Instructor |
|----------|--------|-------|---------|------------|
| `POST /api/tickets` | Crear ticket | ✅ | ✅ | ✅ |
| `GET /api/tickets/{id}` | Consultar por ID | ✅ | ✅ | ✅ |
| `GET /api/tickets` | Listar todos | ✅ | ✅ | ✅ |
| `GET /api/tickets/activos` | Listar activos | ✅ | ✅ | ✅ |
| `DELETE /api/tickets/{id}` | Eliminar | ✅ | ❌ | ❌ |

---

### 3. **ElementoController** (`/api/elementos`)

| Endpoint | Método | Admin | Tecnico | Instructor |
|----------|--------|-------|---------|------------|
| `POST /api/elementos` | Crear elemento | ✅ | ✅ | ✅ |
| `GET /api/elementos/{id}` | Consultar por ID | ✅ | ✅ | ✅ |
| `GET /api/elementos` | Listar todos | ✅ | ✅ | ✅ |
| `DELETE /api/elementos/{id}` | Eliminar | ✅ | ❌ | ❌ |

---

### 4. **PrestamosController** (`/api/prestamos`)

| Endpoint | Método | Admin | Tecnico | Instructor |
|----------|--------|-------|---------|------------|
| `POST /api/prestamos` | Crear préstamo | ✅ | ✅ | ✅ |
| `GET /api/prestamos/{id}` | Consultar por ID | ✅ | ✅ | ✅ |
| `GET /api/prestamos` | Listar todos | ✅ | ✅ | ✅ |
| `GET /api/prestamos/activos` | Listar activos | ✅ | ✅ | ✅ |
| `DELETE /api/prestamos/{id}` | Eliminar | ✅ | ❌ | ❌ |

---

### 5. **SolicitudesController** (`/api/solicitudes`)

| Endpoint | Método | Admin | Tecnico | Instructor |
|----------|--------|-------|---------|------------|
| `POST /api/solicitudes` | Crear solicitud | ✅ | ✅ | ✅ |
| `GET /api/solicitudes/{id}` | Consultar por ID | ✅ | ✅ | ✅ |
| `GET /api/solicitudes` | Listar todas | ✅ | ✅ | ✅ |
| `PUT /api/solicitudes/{id}` | Actualizar estado | ✅ | ✅ | ❌ |
| `DELETE /api/solicitudes/{id}` | Eliminar | ✅ | ❌ | ❌ |
| `POST /api/solicitudes/expirar` | Expirar vencidas | ✅ | ✅ | ✅ |

---

### 6. **CategoriaController** (`/api/categoria`)

| Endpoint | Método | Admin | Tecnico | Instructor |
|----------|--------|-------|---------|------------|
| `POST /api/categoria` | Crear categoría | ✅ | ✅ | ✅ |
| `GET /api/categoria/{id}` | Consultar por ID | ✅ | ✅ | ✅ |
| `GET /api/categoria` | Listar todas | ✅ | ✅ | ✅ |
| `DELETE /api/categoria/{id}` | Eliminar | ✅ | ❌ | ❌ |

---

### 7. **SubcategoriaController** (`/api/subcategoria`)

| Endpoint | Método | Admin | Tecnico | Instructor |
|----------|--------|-------|---------|------------|
| `POST /api/subcategoria` | Crear subcategoría | ✅ | ✅ | ✅ |
| `GET /api/subcategoria/{id}` | Consultar por ID | ✅ | ✅ | ✅ |
| `GET /api/subcategoria` | Listar todas | ✅ | ✅ | ✅ |
| `DELETE /api/subcategoria/{id}` | Eliminar | ✅ | ❌ | ❌ |

---

## ⚙️ CONFIGURACIÓN TÉCNICA

### SecurityConfig.java

**Configuración principal:**
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // ← Habilita @PreAuthorize
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/Usuarios").permitAll()
                
                // Todas las rutas API requieren autenticación
                // (Permisos específicos en @PreAuthorize de cada método)
                .requestMatchers("/api/**").authenticated()
                
                // Rutas de vistas por rol
                .requestMatchers("/admin", "/adcrear", "/Inventario", 
                                 "/Solielemento", "/Soliespacio")
                    .hasRole("ADMINISTRADOR")
                    
                .requestMatchers("/Prestamos-Tecnico", "/Tickets-Tecnico", 
                                 "/TicketsActivos", "/PrestamosActivos", "/HistorialTec")
                    .hasRole("TECNICO")
                
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

---

## 🔍 CÓMO FUNCIONA

### 1. **Autenticación**
- Usuario hace login en `/auth/login`
- Recibe un JWT token con su rol embebido
- Token válido por **1 hora** (configurable en `application.properties`)

### 2. **Autorización**
- Cada petición incluye el token en header: `Authorization: Bearer <token>`
- `JwtRequestFilter` valida el token y extrae el rol
- `@PreAuthorize` en el método del controlador verifica si el rol tiene permiso
- Si no tiene permiso → **403 Forbidden**

### 3. **Respuesta de Error**
```json
{
  "error": true,
  "message": "Acceso denegado: no tienes permisos para esta ruta."
}
```

---

## 🧪 PRUEBAS RECOMENDADAS

### Caso 1: Admin puede hacer TODO
```bash
# Login como admin
POST /auth/login
{ "username": "administrador", "password": "admin123" }

# Obtener token y probar TODOS los endpoints
GET /api/Usuarios        → ✅ 200 OK
POST /api/tickets        → ✅ 201 Created
PUT /api/solicitudes/1   → ✅ 200 OK
DELETE /api/elementos/1  → ✅ 204 No Content
```

### Caso 2: Tecnico NO puede DELETE
```bash
# Login como tecnico
POST /auth/login
{ "username": "tecnico", "password": "tecnico123" }

# Probar operaciones
GET /api/prestamos       → ✅ 200 OK
POST /api/tickets        → ✅ 201 Created
PUT /api/solicitudes/1   → ✅ 200 OK
DELETE /api/elementos/1  → ❌ 403 Forbidden
```

### Caso 3: Instructor solo CREATE y READ
```bash
# Login como instructor
POST /auth/login
{ "username": "instructor", "password": "instructor123" }

# Probar operaciones
GET /api/elementos       → ✅ 200 OK
POST /api/prestamos      → ✅ 201 Created
PUT /api/solicitudes/1   → ❌ 403 Forbidden
DELETE /api/tickets/1    → ❌ 403 Forbidden
```

---

## 📊 ESTADÍSTICAS DEL SISTEMA

- **Controladores con seguridad:** 7
- **Endpoints protegidos:** 29+
- **Roles configurados:** 3
- **Niveles de seguridad:** 2 (Rutas + Métodos)

---

## ✅ CUMPLIMIENTO DE REQUERIMIENTOS

| Requerimiento | Estado |
|---------------|--------|
| Admin: CRUD completo + DELETE | ✅ Implementado |
| Tecnico: CREATE + READ + UPDATE (sin DELETE) | ✅ Implementado |
| Instructor: Solo CREATE + READ | ✅ Implementado |
| Seguridad a nivel de método | ✅ Implementado |
| JWT con roles | ✅ Funcionando |
| Respuestas 403 personalizadas | ✅ Configurado |

---

## 🚀 ESTADO FINAL

**✅ Sistema 100% funcional y coherente con tus especificaciones:**

- ✅ Administrador: Full CRUD + Delete
- ✅ Tecnico: CRUD sin Delete
- ✅ Instructor: Solo Create y Read
- ✅ Seguridad JWT + Spring Security
- ✅ @PreAuthorize en todos los endpoints críticos
- ✅ Sin errores de compilación

---

**Fecha de configuración:** 2025
**Versión Spring Boot:** 3.5.5
**Versión Java:** 21
