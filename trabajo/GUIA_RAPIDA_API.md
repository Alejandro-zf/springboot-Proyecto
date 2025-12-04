# 🚀 GUÍA RÁPIDA - API REST BACKEND

## 📌 BASE URL
```
http://localhost:8080/api
```

---

## 🔐 1. AUTENTICACIÓN

### Login
```http
POST /auth/login
Content-Type: application/json

{
  "correo": "instructor@tech.com",
  "password": "123456"
}
```
**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "usuario": {
    "id": 1,
    "nombre": "Juan",
    "correo": "instructor@tech.com"
  }
}
```

### Registro
```http
POST /auth/register
Content-Type: application/json

{
  "nom_us": "Maria",
  "ape_us": "Garcia",
  "corre": "maria@tech.com",
  "password": "123456"
}
```

---

## 👥 2. USUARIOS

### Crear Usuario
```http
POST /Usuarios
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_us": "Pedro",
  "ape_us": "Lopez",
  "corre": "pedro@tech.com",
  "password": "123456",
  "id_rl": 2
}
```

### Listar Usuarios
```http
GET /Usuarios
Authorization: Bearer {token}
```

### Obtener Usuario por ID
```http
GET /Usuarios/1
Authorization: Bearer {token}
```

### Actualizar Mi Perfil
```http
PUT /Usuarios/perfil/me
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_us": "Pedro Actualizado",
  "ape_us": "Lopez",
  "corre": "pedro@tech.com",
  "currentPassword": "123456",
  "password": "nueva123"
}
```

### Eliminar Usuario
```http
DELETE /Usuarios/1
Authorization: Bearer {token}
```

---

## 📦 3. ELEMENTOS (EQUIPOS)

### Crear Elemento
```http
POST /Elementos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_eleme": "Laptop HP",
  "num_seri": "ABC123456",
  "marc": "HP",
  "obse": "Equipo nuevo",
  "componen": "Intel i5, 8GB RAM, 256GB SSD",
  "est": 1,
  "id_categ": 1,
  "id_subcat": 2
}
```

### Listar Elementos
```http
GET /Elementos
```

### Obtener Elemento por ID
```http
GET /Elementos/1
```

### Actualizar Elemento
```http
PUT /Elementos/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_eleme": "Laptop HP Actualizada",
  "est": 1
}
```

### Eliminar Elemento
```http
DELETE /Elementos/1
Authorization: Bearer {token}
```

### Filtrar por Categoría
```http
GET /Elementos/categoria/1
```

---

## 📁 4. CATEGORÍAS

### Crear Categoría
```http
POST /Categorias
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_categ": "Multimedia",
  "descrip_categ": "Equipos de audio y video"
}
```

### Listar Categorías
```http
GET /Categorias
```

### Obtener Categoría
```http
GET /Categorias/1
```

### Actualizar Categoría
```http
PUT /Categorias/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_categ": "Multimedia Actualizada"
}
```

### Eliminar Categoría
```http
DELETE /Categorias/1
Authorization: Bearer {token}
```

---

## 🏷️ 5. SUBCATEGORÍAS

### Crear Subcategoría
```http
POST /Subcategorias
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_subcateg": "Portátiles",
  "id_categ": 1
}
```

### Listar Subcategorías
```http
GET /Subcategorias
```

### Obtener Subcategoría
```http
GET /Subcategorias/1
```

### Actualizar Subcategoría
```http
PUT /Subcategorias/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_subcateg": "Portátiles Actualizados"
}
```

### Eliminar Subcategoría
```http
DELETE /Subcategorias/1
Authorization: Bearer {token}
```

---

## 📝 6. SOLICITUDES

### Crear Solicitud
```http
POST /Solicitudes
Authorization: Bearer {token}
Content-Type: application/json

{
  "fecha_ini": "2025-12-10T08:00:00",
  "fecha_fn": "2025-12-10T16:00:00",
  "ambient": "Aula 301",
  "num_ficha": "2560014",
  "id_usu": 1,
  "estadosoli": 1,
  "id_elemen": [1, 2, 3]
}
```

### Listar Solicitudes
```http
GET /Solicitudes
Authorization: Bearer {token}
```

### Obtener Solicitud por ID
```http
GET /Solicitudes/1
Authorization: Bearer {token}
```

### Aprobar/Rechazar Solicitud
```http
PUT /Solicitudes/actualizar/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "id_est_soli": 2
}
```
**Estados:**
- 1 = Pendiente
- 2 = Aprobado
- 3 = Rechazado
- 4 = Cancelado
- 5 = En uso
- 6 = Finalizado

### Cancelar Solicitud
```http
PUT /Solicitudes/cancelar/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "id_est_soli": 4
}
```

---

## 🏢 7. ESPACIOS

### Crear Espacio
```http
POST /Espacios
Authorization: Bearer {token}
Content-Type: application/json

{
  "nom_espacio": "Auditorio Principal",
  "capac": 100,
  "descrip_espacio": "Auditorio con proyector"
}
```

### Listar Espacios
```http
GET /Espacios
```

### Obtener Espacio
```http
GET /Espacios/1
```

### Actualizar Espacio
```http
PUT /Espacios/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "capac": 150
}
```

### Eliminar Espacio
```http
DELETE /Espacios/1
Authorization: Bearer {token}
```

---

## 🎫 8. TICKETS (REPORTES)

### Crear Ticket
```http
POST /Tickets
Authorization: Bearer {token}
Content-Type: application/json

{
  "ambient": "Aula 201",
  "Obser": "Pantalla no enciende",
  "id_usuario": 1,
  "id_eleme": 5,
  "id_problm": 1,
  "id_est_tick": 2
}
```

### Listar Tickets
```http
GET /Tickets
Authorization: Bearer {token}
```

### Listar Tickets Activos
```http
GET /Tickets/activos
Authorization: Bearer {token}
```

### Obtener Ticket por ID
```http
GET /Tickets/1
Authorization: Bearer {token}
```

### Actualizar Ticket
```http
PUT /Tickets/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "Obser": "Problema resuelto - cable suelto"
}
```

### Cambiar Estado de Ticket
```http
PUT /Tickets/1/estado
Authorization: Bearer {token}
Content-Type: application/json

{
  "id_est_tick": 1
}
```
**Estados:**
- 1 = Resuelto
- 2 = Activo

### Eliminar Ticket
```http
DELETE /Tickets/1
Authorization: Bearer {token}
```

---

## 🔄 9. PRÉSTAMOS

### Crear Préstamo
```http
POST /Prestamos
Authorization: Bearer {token}
Content-Type: application/json

{
  "fecha_pres": "2025-12-04T10:00:00",
  "fecha_dev": "2025-12-04T18:00:00",
  "obser": "Préstamo para proyecto",
  "id_usu": 1,
  "id_espa": 1,
  "id_elemen": [1, 2]
}
```

### Listar Préstamos
```http
GET /Prestamos
Authorization: Bearer {token}
```

### Listar Préstamos Activos
```http
GET /Prestamos/activos
Authorization: Bearer {token}
```

### Obtener Préstamo
```http
GET /Prestamos/1
Authorization: Bearer {token}
```

### Actualizar Préstamo
```http
PUT /Prestamos/1
Authorization: Bearer {token}
Content-Type: application/json

{
  "fecha_dev": "2025-12-05T18:00:00"
}
```

### Eliminar Préstamo
```http
DELETE /Prestamos/1
Authorization: Bearer {token}
```

---

## 🔧 10. CÓDIGOS DE RESPUESTA HTTP

| Código | Significado |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado |
| 204 | No Content - Eliminado exitosamente |
| 400 | Bad Request - Datos inválidos |
| 401 | Unauthorized - Token inválido/expirado |
| 403 | Forbidden - Sin permisos |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |

---

## 📋 11. FORMATO DE ERRORES

```json
{
  "timestamp": "2025-12-04T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El correo ya está registrado",
  "path": "/api/Usuarios"
}
```

---

## 💡 12. EJEMPLOS RÁPIDOS CON CURL

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"correo":"instructor@tech.com","password":"123456"}'
```

### Crear Elemento
```bash
curl -X POST http://localhost:8080/api/Elementos \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{"nom_eleme":"Mouse","num_seri":"M001","marc":"Logitech","est":1,"id_categ":1,"id_subcat":1}'
```

### Listar Solicitudes
```bash
curl -X GET http://localhost:8080/api/Solicitudes \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

---

## 📝 NOTAS IMPORTANTES

1. **Token JWT:** La mayoría de endpoints requieren el header `Authorization: Bearer {token}`
2. **Fechas:** Formato ISO 8601: `YYYY-MM-DDTHH:mm:ss`
3. **IDs:** Los IDs de creación automática no deben enviarse en POST
4. **Validación:** El backend valida campos requeridos automáticamente

---

**¡Listo para usar! 🚀**
