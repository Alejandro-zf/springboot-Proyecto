# 📊 MATRIZ DE OPERACIONES CRUD - BACKEND API

## 🎯 Resumen de Permisos por Módulo

Esta matriz define las operaciones permitidas en cada módulo del sistema.

---

## 📋 TABLA DE PERMISOS

| Módulo | Crear | Consultar | Actualizar | Eliminar | Notas |
|--------|:-----:|:---------:|:----------:|:--------:|-------|
| **Usuarios** | ✅ | ✅ | ✅ | ✅ | CRUD completo |
| **Elementos** | ✅ | ✅ | ❌ | ✅ | NO puede actualizar |
| **Categorías** | ✅ | ✅ | ❌ | ✅ | NO puede actualizar |
| **Subcategorías** | ✅ | ✅ | ❌ | ✅ | NO puede actualizar |
| **Solicitudes** | ❌ | ✅ | ❌ | ❌ | Solo consulta |
| **Tickets** | ❌ | ✅ | ❌ | ❌ | Solo consulta |
| **Préstamos** | ❌ | ✅ | ❌ | ❌ | Solo consulta |
| **Espacios** | ❌ | ✅ | ❌ | ❌ | Solo consulta |

---

## 🔧 OPERACIONES ESPECIALES

### ✅ Permitidas
- **Expirar solicitudes vencidas:** ✅ `POST /api/Solicitudes/expirar`
- **Actualizar mi perfil:** ✅ `PUT /api/Usuarios/perfil/me`

### ❌ Restringidas
- **Actualizar elementos:** ❌ No permitido
- **Crear/Actualizar/Eliminar solicitudes:** ❌ Solo lectura
- **Crear/Actualizar/Eliminar tickets:** ❌ Solo lectura
- **Crear/Actualizar/Eliminar préstamos:** ❌ Solo lectura

---

## 📝 DETALLE POR MÓDULO

### 1️⃣ USUARIOS (CRUD Completo)
✅ **Crear** - `POST /api/Usuarios`
```json
{
  "nom_us": "Pedro",
  "ape_us": "Lopez",
  "corre": "pedro@tech.com",
  "password": "123456",
  "id_rl": 2
}
```

✅ **Consultar Todos** - `GET /api/Usuarios`

✅ **Consultar por ID** - `GET /api/Usuarios/{id}`

✅ **Actualizar** - `PUT /api/Usuarios/{id}`
```json
{
  "nom_us": "Pedro Actualizado",
  "ape_us": "Lopez"
}
```

✅ **Actualizar Mi Perfil** - `PUT /api/Usuarios/perfil/me`
```json
{
  "nom_us": "Pedro",
  "currentPassword": "123456",
  "password": "nueva123"
}
```

✅ **Eliminar** - `DELETE /api/Usuarios/{id}`

---

### 2️⃣ ELEMENTOS (Crear, Consultar, Eliminar)
✅ **Crear** - `POST /api/Elementos`
```json
{
  "nom_eleme": "Laptop HP",
  "num_seri": "ABC123",
  "marc": "HP",
  "est": 1,
  "id_categ": 1,
  "id_subcat": 2
}
```

✅ **Consultar Todos** - `GET /api/Elementos`

✅ **Consultar por ID** - `GET /api/Elementos/{id}`

✅ **Filtrar por Categoría** - `GET /api/Elementos/categoria/{id}`

✅ **Filtrar por Subcategoría** - `GET /api/Elementos/subcategoria/{id}`

❌ **Actualizar** - NO PERMITIDO

✅ **Eliminar** - `DELETE /api/Elementos/{id}`

---

### 3️⃣ CATEGORÍAS (Crear, Consultar, Eliminar)
✅ **Crear** - `POST /api/Categorias`
```json
{
  "nom_categ": "Multimedia",
  "descrip_categ": "Equipos de audio y video"
}
```

✅ **Consultar Todas** - `GET /api/Categorias`

✅ **Consultar por ID** - `GET /api/Categorias/{id}`

❌ **Actualizar** - NO PERMITIDO

✅ **Eliminar** - `DELETE /api/Categorias/{id}`

---

### 4️⃣ SUBCATEGORÍAS (Crear, Consultar, Eliminar)
✅ **Crear** - `POST /api/Subcategorias`
```json
{
  "nom_subcateg": "Portátiles",
  "id_categ": 1
}
```

✅ **Consultar Todas** - `GET /api/Subcategorias`

✅ **Consultar por ID** - `GET /api/Subcategorias/{id}`

❌ **Actualizar** - NO PERMITIDO

✅ **Eliminar** - `DELETE /api/Subcategorias/{id}`

---

### 5️⃣ SOLICITUDES (Solo Consultar)
❌ **Crear** - NO PERMITIDO

✅ **Consultar Todas** - `GET /api/Solicitudes`

✅ **Consultar por ID** - `GET /api/Solicitudes/{id}`

❌ **Actualizar** - NO PERMITIDO

❌ **Eliminar** - NO PERMITIDO

#### Operación Especial:
✅ **Expirar Solicitudes Vencidas** - `POST /api/Solicitudes/expirar`
```json
{
  "mensaje": "Solicitudes expiradas procesadas correctamente"
}
```

---

### 6️⃣ TICKETS (Solo Consultar)
❌ **Crear** - NO PERMITIDO

✅ **Consultar Todos** - `GET /api/Tickets`

✅ **Consultar por ID** - `GET /api/Tickets/{id}`

✅ **Consultar Activos** - `GET /api/Tickets/activos`

✅ **Consultar Pendientes** - `GET /api/Tickets/pendientes`

❌ **Actualizar** - NO PERMITIDO

❌ **Eliminar** - NO PERMITIDO

---

### 7️⃣ PRÉSTAMOS (Solo Consultar)
❌ **Crear** - NO PERMITIDO

✅ **Consultar Todos** - `GET /api/Prestamos`

✅ **Consultar por ID** - `GET /api/Prestamos/{id}`

✅ **Consultar Activos** - `GET /api/Prestamos/activos`

❌ **Actualizar** - NO PERMITIDO

❌ **Eliminar** - NO PERMITIDO

---

### 8️⃣ ESPACIOS (Solo Consultar)
❌ **Crear** - NO PERMITIDO

✅ **Consultar Todos** - `GET /api/Espacios`

✅ **Consultar por ID** - `GET /api/Espacios/{id}`

❌ **Actualizar** - NO PERMITIDO

❌ **Eliminar** - NO PERMITIDO

---

## 🔒 AUTENTICACIÓN

Todos los endpoints requieren autenticación JWT excepto:
- `POST /api/auth/login`
- `POST /api/auth/register`
- `GET /api/Elementos` (consulta pública)
- `GET /api/Categorias` (consulta pública)
- `GET /api/Subcategorias` (consulta pública)

---

## 📊 RESUMEN ESTADÍSTICO

| Operación | Módulos que la permiten |
|-----------|-------------------------|
| **Crear** | Usuarios, Elementos, Categorías, Subcategorías (4) |
| **Consultar** | Todos los módulos (8) |
| **Actualizar** | Solo Usuarios (1) |
| **Eliminar** | Usuarios, Elementos, Categorías, Subcategorías (4) |

---

## ⚠️ RESPUESTAS DE ERROR

### Operación No Permitida
```json
{
  "timestamp": "2025-12-04T10:30:00",
  "status": 405,
  "error": "Method Not Allowed",
  "message": "Esta operación no está permitida en este módulo",
  "path": "/api/Elementos/1"
}
```

---

## 💡 CASOS DE USO

### ✅ PERMITIDO
```bash
# Crear un elemento
POST /api/Elementos
✅ Código 201 - Created

# Consultar solicitudes
GET /api/Solicitudes
✅ Código 200 - OK

# Eliminar categoría
DELETE /api/Categorias/1
✅ Código 204 - No Content
```

### ❌ NO PERMITIDO
```bash
# Intentar actualizar un elemento
PUT /api/Elementos/1
❌ Código 405 - Method Not Allowed

# Intentar crear una solicitud
POST /api/Solicitudes
❌ Código 405 - Method Not Allowed

# Intentar eliminar un ticket
DELETE /api/Tickets/1
❌ Código 405 - Method Not Allowed
```

---

**Versión:** 1.0  
**Última actualización:** Diciembre 2025  
**Sistema:** Tech Inventory Management
