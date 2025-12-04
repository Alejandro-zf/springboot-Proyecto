# 📋 REQUERIMIENTOS FUNCIONALES - BACKEND API REST

## 🎯 Descripción General
Sistema backend desarrollado en Spring Boot para la gestión de inventario tecnológico, préstamos, solicitudes y tickets de soporte en un centro educativo.

**Tecnologías:** Spring Boot, Spring Security, JWT, MySQL, JPA/Hibernate

---

## 🔐 1. MÓDULO DE AUTENTICACIÓN Y USUARIOS

### 1.1 Autenticación (AuthController)
- **RF-AUTH-01:** Login de usuarios con correo y contraseña
  - Endpoint: `POST /api/auth/login`
  - Genera token JWT para sesiones autenticadas
  - Valida credenciales contra base de datos encriptada (BCrypt)

- **RF-AUTH-02:** Registro de nuevos usuarios
  - Endpoint: `POST /api/auth/register`
  - Encripta contraseñas automáticamente
  - Asigna rol por defecto

### 1.2 Gestión de Usuarios (UsuariosController)
- **RF-USR-01:** Crear usuario
  - Endpoint: `POST /api/Usuarios`
  - Validación de correo único
  - Encriptación de contraseña

- **RF-USR-02:** Obtener usuario por ID
  - Endpoint: `GET /api/Usuarios/{id}`

- **RF-USR-03:** Listar todos los usuarios
  - Endpoint: `GET /api/Usuarios`

- **RF-USR-04:** Actualizar usuario (Admin)
  - Endpoint: `PUT /api/Usuarios/{id}`
  - Permite cambio de rol y datos completos

- **RF-USR-05:** Actualizar perfil propio
  - Endpoint: `PUT /api/Usuarios/perfil/me`
  - **Seguridad:** Valida contraseña actual antes de cambiar a nueva
  - No permite cambio de rol propio
  - Requiere autenticación JWT

- **RF-USR-06:** Eliminar usuario
  - Endpoint: `DELETE /api/Usuarios/{id}`

- **RF-USR-07:** Carga masiva de usuarios desde Excel
  - Endpoint: `POST /api/Usuarios/upload`
  - Acepta archivos .xlsx
  - Validación de formato

- **RF-USR-08:** Descargar plantilla Excel
  - Endpoint: `GET /api/Usuarios/template`

- **RF-USR-09:** Obtener encabezados de plantilla
  - Endpoint: `GET /api/Usuarios/template/headers`

---

## 📦 2. MÓDULO DE ELEMENTOS E INVENTARIO

### 2.1 Gestión de Elementos (ElementoController)
- **RF-ELEM-01:** Crear elemento
  - Endpoint: `POST /api/Elementos`
  - Asocia a categoría y subcategoría

- **RF-ELEM-02:** Obtener elemento por ID
  - Endpoint: `GET /api/Elementos/{id}`

- **RF-ELEM-03:** Listar todos los elementos
  - Endpoint: `GET /api/Elementos`

- **RF-ELEM-04:** Actualizar elemento
  - Endpoint: `PUT /api/Elementos/{id}`

- **RF-ELEM-05:** Eliminar elemento
  - Endpoint: `DELETE /api/Elementos/{id}`

- **RF-ELEM-06:** Filtrar elementos por categoría
  - Endpoint: `GET /api/Elementos/categoria/{id}`

- **RF-ELEM-07:** Filtrar elementos por subcategoría
  - Endpoint: `GET /api/Elementos/subcategoria/{id}`

### 2.2 Categorías (CategoriaController)
- **RF-CAT-01:** Crear categoría
  - Endpoint: `POST /api/Categorias`

- **RF-CAT-02:** Listar categorías
  - Endpoint: `GET /api/Categorias`

- **RF-CAT-03:** Obtener categoría por ID
  - Endpoint: `GET /api/Categorias/{id}`

- **RF-CAT-04:** Actualizar categoría
  - Endpoint: `PUT /api/Categorias/{id}`

- **RF-CAT-05:** Eliminar categoría
  - Endpoint: `DELETE /api/Categorias/{id}`

### 2.3 Subcategorías (SubcategoriaController)
- **RF-SUBCAT-01:** Crear subcategoría
  - Endpoint: `POST /api/Subcategorias`

- **RF-SUBCAT-02:** Listar subcategorías
  - Endpoint: `GET /api/Subcategorias`

- **RF-SUBCAT-03:** Obtener subcategoría por ID
  - Endpoint: `GET /api/Subcategorias/{id}`

- **RF-SUBCAT-04:** Actualizar subcategoría
  - Endpoint: `PUT /api/Subcategorias/{id}`

- **RF-SUBCAT-05:** Eliminar subcategoría
  - Endpoint: `DELETE /api/Subcategorias/{id}`

---

## 📝 3. MÓDULO DE SOLICITUDES

### 3.1 Gestión de Solicitudes (SolicitudesController)
- **RF-SOL-01:** Crear solicitud
  - Endpoint: `POST /api/Solicitudes`
  - Permite solicitudes de elementos o espacios
  - Asigna estado "Pendiente" por defecto

- **RF-SOL-02:** Obtener solicitud por ID
  - Endpoint: `GET /api/Solicitudes/{id}`
  - Incluye relación con elementos asociados

- **RF-SOL-03:** Listar todas las solicitudes
  - Endpoint: `GET /api/Solicitudes`

- **RF-SOL-04:** Actualizar estado de solicitud (Instructor)
  - Endpoint: `PUT /api/Solicitudes/actualizar/{id}`
  - Estados: Pendiente, Aprobado, Rechazado, En uso, Finalizado, Cancelado

- **RF-SOL-05:** Actualizar datos de solicitud
  - Endpoint: `PUT /api/Solicitudes/{id}`

- **RF-SOL-06:** Cancelar solicitud
  - Endpoint: `PUT /api/Solicitudes/cancelar/{id}`
  - Solo usuarios autenticados pueden cancelar sus propias solicitudes
  - Valida que la solicitud esté en estado "Pendiente"

- **RF-SOL-07:** Expirar solicitudes automáticamente
  - Endpoint: `POST /api/Solicitudes/expirar`
  - Cambia a estado "Finalizado" las solicitudes cuya fecha de fin ya pasó

### 3.2 Relación Elementos-Solicitudes (Elemento_solicitudesController)
- **RF-ELSOL-01:** Asociar elemento a solicitud
  - Endpoint: `POST /api/ElementosSolicitudes`

- **RF-ELSOL-02:** Asociar múltiples elementos a solicitud
  - Endpoint: `POST /api/ElementosSolicitudes/Varios`

- **RF-ELSOL-03:** Obtener elementos de una solicitud
  - Endpoint: `GET /api/ElementosSolicitudes/solicitudes/{id}`

- **RF-ELSOL-04:** Obtener solicitudes de un elemento
  - Endpoint: `GET /api/ElementosSolicitudes/elementos/{id}`

- **RF-ELSOL-05:** Eliminar asociación elemento-solicitud
  - Endpoint: `DELETE /api/ElementosSolicitudes/{solicitudId}/{elementoId}`

---

## 🏢 4. MÓDULO DE ESPACIOS

### 4.1 Gestión de Espacios (EspacioController)
- **RF-ESP-01:** Crear espacio
  - Endpoint: `POST /api/Espacios`

- **RF-ESP-02:** Obtener espacio por ID
  - Endpoint: `GET /api/Espacios/{id}`

- **RF-ESP-03:** Listar todos los espacios
  - Endpoint: `GET /api/Espacios`

- **RF-ESP-04:** Actualizar espacio
  - Endpoint: `PUT /api/Espacios/{id}`

- **RF-ESP-05:** Eliminar espacio
  - Endpoint: `DELETE /api/Espacios/{id}`

---

## 🎫 5. MÓDULO DE TICKETS Y SOPORTE

### 5.1 Gestión de Tickets (TicketsController)
- **RF-TICK-01:** Crear ticket de problema
  - Endpoint: `POST /api/Tickets`
  - Permite reportar problemas en equipos
  - Asocia usuario, elemento y problema

- **RF-TICK-02:** Subir imágenes de evidencia
  - Endpoint: `POST /api/Tickets/upload-images`
  - Acepta múltiples imágenes
  - Almacena URLs en base de datos

- **RF-TICK-03:** Obtener ticket por ID
  - Endpoint: `GET /api/Tickets/{id}`

- **RF-TICK-04:** Listar todos los tickets
  - Endpoint: `GET /api/Tickets`

- **RF-TICK-05:** Listar tickets activos
  - Endpoint: `GET /api/Tickets/activos`

- **RF-TICK-06:** Listar tickets pendientes
  - Endpoint: `GET /api/Tickets/pendientes`

- **RF-TICK-07:** Actualizar ticket
  - Endpoint: `PUT /api/Tickets/{id}`

- **RF-TICK-08:** Cambiar estado de ticket
  - Endpoint: `PUT /api/Tickets/{id}/estado`

- **RF-TICK-09:** Eliminar ticket
  - Endpoint: `DELETE /api/Tickets/{id}`

### 5.2 Problemas (ProblemasController)
- **RF-PROB-01:** Obtener problema por descripción
  - Endpoint: `GET /api/Problemas/descripcion`

- **RF-PROB-02:** Listar todos los problemas
  - Endpoint: `GET /api/Problemas`

### 5.3 Estados de Tickets (TicketsEstadoController)
- **RF-TICKEST-01:** Gestión completa de estados de tickets
  - Endpoints CRUD para estados personalizados

---

## 📋 6. MÓDULO DE PRÉSTAMOS

### 6.1 Gestión de Préstamos (PrestamosController)
- **RF-PREST-01:** Crear préstamo
  - Endpoint: `POST /api/Prestamos`
  - Registra préstamo de equipos a usuarios
  - Valida disponibilidad de elementos

- **RF-PREST-02:** Obtener préstamo por ID
  - Endpoint: `GET /api/Prestamos/{id}`

- **RF-PREST-03:** Listar todos los préstamos
  - Endpoint: `GET /api/Prestamos`

- **RF-PREST-04:** Listar préstamos activos
  - Endpoint: `GET /api/Prestamos/activos`

- **RF-PREST-05:** Actualizar préstamo
  - Endpoint: `PUT /api/Prestamos/{id}`

- **RF-PREST-06:** Eliminar préstamo
  - Endpoint: `DELETE /api/Prestamos/{id}`

### 6.2 Relación Préstamos-Elementos (Prestamos_ElementoController)
- **RF-PRELEM-01:** Asociar elemento a préstamo
  - Endpoint: `POST /api/PrestamosElementos`

- **RF-PRELEM-02:** Asociar múltiples elementos a préstamo
  - Endpoint: `POST /api/PrestamosElementos/Varios`

- **RF-PRELEM-03:** Obtener elementos de un préstamo
  - Endpoint: `GET /api/PrestamosElementos/prestamos/{id}`

- **RF-PRELEM-04:** Obtener préstamos de un elemento
  - Endpoint: `GET /api/PrestamosElementos/elementos/{id}`

- **RF-PRELEM-05:** Eliminar asociación préstamo-elemento
  - Endpoint: `DELETE /api/PrestamosElementos/{prestamosId}/{elementosId}`

---

## 🔍 7. MÓDULO DE TRAZABILIDAD

### 7.1 Auditoría (TrasabilidadController)
- **RF-TRAZ-01:** Obtener trazabilidad por ID
  - Endpoint: `GET /api/Trasabilidad/{id}`

- **RF-TRAZ-02:** Obtener trazabilidad por ticket
  - Endpoint: `GET /api/Trasabilidad/ticket/{id}`

- **RF-TRAZ-03:** Listar toda la trazabilidad
  - Endpoint: `GET /api/Trasabilidad`

- **RF-TRAZ-04:** Actualizar trazabilidad
  - Endpoint: `PUT /api/Trasabilidad/{id}`

- **RF-TRAZ-05:** Eliminar registro de trazabilidad
  - Endpoint: `DELETE /api/Trasabilidad/{id}`

---

## 🔧 8. CARACTERÍSTICAS TÉCNICAS

### 8.1 Seguridad
- ✅ Autenticación JWT (JSON Web Tokens)
- ✅ Encriptación de contraseñas con BCrypt
- ✅ Validación de contraseña actual en cambios de perfil
- ✅ Control de acceso basado en roles (RBAC)
- ✅ Protección CSRF
- ✅ CORS configurado

### 8.2 Validaciones
- ✅ Validación de correos únicos
- ✅ Validación de datos con Bean Validation (@Valid)
- ✅ Manejo de excepciones centralizado
- ✅ Validación de relaciones (FK constraints)

### 8.3 Base de Datos
- ✅ ORM con JPA/Hibernate
- ✅ Transacciones ACID
- ✅ Migraciones con SQL scripts
- ✅ Relaciones Many-to-Many con tablas intermedias

### 8.4 Funcionalidades Adicionales
- ✅ Carga masiva de datos desde Excel
- ✅ Subida de imágenes para tickets
- ✅ Expiración automática de solicitudes
- ✅ Generación de plantillas Excel
- ✅ Trazabilidad completa de operaciones

---

## 📊 RESUMEN DE ENDPOINTS

| Módulo | Controlador | Endpoints |
|--------|------------|-----------|
| Autenticación | AuthController | 2 |
| Usuarios | UsuariosController | 9 |
| Elementos | ElementoController | 7 |
| Categorías | CategoriaController | 5 |
| Subcategorías | SubcategoriaController | 5 |
| Solicitudes | SolicitudesController | 7 |
| Elem-Solicitudes | Elemento_solicitudesController | 5 |
| Espacios | EspacioController | 5 |
| Tickets | TicketsController | 9 |
| Problemas | ProblemasController | 2 |
| Préstamos | PrestamosController | 6 |
| Prest-Elementos | Prestamos_ElementoController | 5 |
| Trazabilidad | TrasabilidadController | 5 |

**Total: 67+ endpoints funcionales**

---

## 📝 NOTAS DE IMPLEMENTACIÓN

### Estados del Sistema
- **Solicitudes:** Pendiente, Aprobado, Rechazado, En uso, Finalizado, Cancelado
- **Tickets:** Activo, Resuelto
- **Elementos:** Activo (1), Inactivo (0)

### Relaciones Clave
- Usuario → Solicitudes (1:N)
- Usuario → Tickets (1:N)
- Usuario → Préstamos (1:N)
- Solicitud → Elementos (N:N)
- Préstamo → Elementos (N:N)
- Categoría → Subcategoría (1:N)
- Subcategoría → Elementos (1:N)

---

**Versión:** 1.0  
**Última actualización:** Diciembre 2025  
**Framework:** Spring Boot 3.x  
**Base de Datos:** MySQL 8.x
