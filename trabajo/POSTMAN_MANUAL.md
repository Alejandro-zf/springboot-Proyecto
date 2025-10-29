# 📮 MANUAL DE PRUEBAS EN POSTMAN - TECH INVENTORY

## 🎯 Guía Completa para Probar el Sistema de Roles y Permisos

---

## 📋 ÍNDICE

1. [Configuración Inicial](#configuración-inicial)
2. [Autenticación y Login](#autenticación-y-login)
3. [Pruebas por Rol](#pruebas-por-rol)
4. [Casos de Prueba Detallados](#casos-de-prueba-detallados)
5. [Respuestas Esperadas](#respuestas-esperadas)
6. [Troubleshooting](#troubleshooting)|||

---

## 🔧 CONFIGURACIÓN INICIAL

### 1. Variables de Entorno en Postman

Crear una nueva **Collection** llamada "Tech Inventory API" y configurar variables:

```
BASE_URL: http://localhost:8081
TOKEN_ADMIN: (se llenará después del login)
TOKEN_TECNICO: (se llenará después del login)
TOKEN_INSTRUCTOR: (se llenará después del login)
```

**Cómo agregar variables:**
1. Click derecho en la Collection → **Edit**
2. Tab **Variables**
3. Agregar las variables arriba mencionadas

---

## 🔐 AUTENTICACIÓN Y LOGIN

### 1️⃣ Login como Administrador

**Endpoint:** `POST {{BASE_URL}}/auth/login`

**Headers:**
```
Content-Type: application/json
```

**Body (raw - JSON):**
```json
{
  "username": "administrador",
  "password": "admin123"
}
```

**Respuesta Esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 2,
  "username": "administrador",
  "rol": "Administrador"
}
```

**Script Post-Response (Tests tab):**
```javascript
// Guardar el token automáticamente
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.collectionVariables.set("TOKEN_ADMIN", jsonData.token);
    console.log("Token Admin guardado: " + jsonData.token);
}
```

---

### 2️⃣ Login como Tecnico

**Endpoint:** `POST {{BASE_URL}}/auth/login`

**Headers:**
```
Content-Type: application/json
```

**Body (raw - JSON):**
```json
{
  "username": "tecnico",
  "password": "tecnico123"
}
```

**Respuesta Esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 3,
  "username": "tecnico",
  "rol": "Tecnico"
}
```

**Script Post-Response (Tests tab):**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.collectionVariables.set("TOKEN_TECNICO", jsonData.token);
    console.log("Token Tecnico guardado: " + jsonData.token);
}
```

---

### 3️⃣ Login como Instructor

**Endpoint:** `POST {{BASE_URL}}/auth/login`

**Headers:**
```
Content-Type: application/json
```

**Body (raw - JSON):**
```json
{
  "username": "instructor",
  "password": "instructor123"
}
```

**Respuesta Esperada (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1,
  "username": "instructor",
  "rol": "Instructor"
}
```

**Script Post-Response (Tests tab):**
```javascript
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    pm.collectionVariables.set("TOKEN_INSTRUCTOR", jsonData.token);
    console.log("Token Instructor guardado: " + jsonData.token);
}
```

---

### 4️⃣ Verificar Autenticación

**Endpoint:** `GET {{BASE_URL}}/auth/me`

**Headers:**
```
Authorization: Bearer {{TOKEN_ADMIN}}
```

**Respuesta Esperada (200 OK):**
```json
{
  "id": 2,
  "username": "administrador",
  "nombre": "Administrador",
  "apellido": "Sistema",
  "correo": "admin@tech.com",
  "rol": "Administrador"
}
```

---

## 🧪 PRUEBAS POR ROL

### 🔴 ROL: ADMINISTRADOR (CRUD Completo + DELETE)

#### ✅ TEST 1: Listar Usuarios (GET)

**Endpoint:** `GET {{BASE_URL}}/api/Usuarios`

**Headers:**
```
Authorization: Bearer {{TOKEN_ADMIN}}
```

**Respuesta Esperada:** `200 OK`
```json
[
  {
    "id_Usu": 1,
    "username": "instructor",
    "nombre": "Instructor",
    "apellido": "Sistema",
    "correo": "instructor@tech.com",
    "num_doc": 1234567890,
    "tipo_doc": "Cédula de Ciudadanía",
    "rol": "Instructor"
  },
  ...
]
```

---

#### ✅ TEST 2: Crear Usuario (POST)

**Endpoint:** `POST {{BASE_URL}}/api/Usuarios`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_ADMIN}}
```

**Body (raw - JSON):**
```json
{
  "username": "nuevo_usuario",
  "nombre": "Nuevo",
  "apellido": "Usuario",
  "correo": "nuevo@tech.com",
  "num_doc": 1112223334,
  "password": "password123",
  "id_Tip_doc": 1,
  "id_rol": 2
}
```

**Respuesta Esperada:** `201 Created`
```json
{
  "Mensaje": "Usuario creado exitosamente",
  "data": {
    "id_Usu": 4,
    "username": "nuevo_usuario",
    "nombre": "Nuevo",
    ...
  }
}
```

---

#### ✅ TEST 3: Actualizar Usuario (PUT)

**Endpoint:** `PUT {{BASE_URL}}/api/Usuarios/4`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_ADMIN}}
```

**Body (raw - JSON):**
```json
{
  "nombre": "Nuevo Actualizado",
  "apellido": "Usuario Modificado",
  "correo": "nuevo_actualizado@tech.com"
}
```

**Respuesta Esperada:** `200 OK`
```json
{
  "id_Usu": 4,
  "username": "nuevo_usuario",
  "nombre": "Nuevo Actualizado",
  "apellido": "Usuario Modificado",
  ...
}
```

---

#### ✅ TEST 4: Eliminar Usuario (DELETE)

**Endpoint:** `DELETE {{BASE_URL}}/api/Usuarios/4`

**Headers:**
```
Authorization: Bearer {{TOKEN_ADMIN}}
```

**Respuesta Esperada:** `204 No Content`

---

#### ✅ TEST 5: Crear Ticket (POST)

**Endpoint:** `POST {{BASE_URL}}/api/tickets`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_ADMIN}}
```

**Body (raw - JSON):**
```json
{
  "id_usuario": 2,
  "id_elemento": 1,
  "id_problema": 1,
  "descripcion": "Pantalla no enciende",
  "estado": 1
}
```

**Respuesta Esperada:** `201 Created`
```json
{
  "mensaje": "Ticket creado exitosamente",
  "data": {
    "id": 1,
    "usuario": "administrador",
    "elemento": "Computador Dell",
    "problema": "Problema de Hardware",
    "descripcion": "Pantalla no enciende",
    "estado": 1
  }
}
```

---

#### ✅ TEST 6: Listar Tickets (GET)

**Endpoint:** `GET {{BASE_URL}}/api/tickets`

**Headers:**
```
Authorization: Bearer {{TOKEN_ADMIN}}
```

**Respuesta Esperada:** `200 OK`
```json
[
  {
    "id": 1,
    "usuario": "administrador",
    "elemento": "Computador Dell",
    ...
  }
]
```

---

#### ✅ TEST 7: Eliminar Ticket (DELETE)

**Endpoint:** `DELETE {{BASE_URL}}/api/tickets/1`

**Headers:**
```
Authorization: Bearer {{TOKEN_ADMIN}}
```

**Respuesta Esperada:** `204 No Content`

---

### 🟡 ROL: TECNICO (CREATE + READ + UPDATE, SIN DELETE)

#### ✅ TEST 8: Listar Elementos (GET)

**Endpoint:** `GET {{BASE_URL}}/api/elementos`

**Headers:**
```
Authorization: Bearer {{TOKEN_TECNICO}}
```

**Respuesta Esperada:** `200 OK`
```json
[
  {
    "id": 1,
    "serial": "SN12345",
    "nom_elemento": "Computador Dell",
    "marca": "Dell",
    ...
  }
]
```

---

#### ✅ TEST 9: Crear Elemento (POST)

**Endpoint:** `POST {{BASE_URL}}/api/elementos`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_TECNICO}}
```

**Body (raw - JSON):**
```json
{
  "serial": "SN99999",
  "nom_elemento": "Laptop HP",
  "marca": "HP",
  "modelo": "Pavilion",
  "estado": 1,
  "id_subcategoria": 1
}
```

**Respuesta Esperada:** `201 Created`
```json
{
  "Mensaje": "Elemento creado exitosamente",
  "data": {
    "id": 2,
    "serial": "SN99999",
    "nom_elemento": "Laptop HP",
    ...
  }
}
```

---

#### ✅ TEST 10: Crear Préstamo (POST)

**Endpoint:** `POST {{BASE_URL}}/api/prestamos`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_TECNICO}}
```

**Body (raw - JSON):**
```json
{
  "id_usuario": 3,
  "fecha_prestamo": "2025-10-29",
  "fecha_devolucion": "2025-11-05",
  "estado": 1
}
```

**Respuesta Esperada:** `201 Created`
```json
{
  "Mensaje": "Prestamo creado exitosamente",
  "data": {
    "id": 1,
    "usuario": "tecnico",
    "fecha_prestamo": "2025-10-29",
    "fecha_devolucion": "2025-11-05",
    "estado": 1
  }
}
```

---

#### ✅ TEST 11: Actualizar Solicitud (PUT)

**Endpoint:** `PUT {{BASE_URL}}/api/solicitudes/1`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_TECNICO}}
```

**Body (raw - JSON):**
```json
{
  "estado": 2
}
```

**Respuesta Esperada:** `200 OK`
```json
{
  "mensaje": "Solicitud actualizada",
  "data": {
    "id": 1,
    "estado": 2,
    ...
  }
}
```

---

#### ❌ TEST 12: Intentar Eliminar Elemento (DELETE) - DEBE FALLAR

**Endpoint:** `DELETE {{BASE_URL}}/api/elementos/1`

**Headers:**
```
Authorization: Bearer {{TOKEN_TECNICO}}
```

**Respuesta Esperada:** `403 Forbidden`
```json
{
  "error": true,
  "message": "Acceso denegado: no tienes permisos para esta ruta."
}
```

**✅ Este error es CORRECTO - Tecnico NO puede eliminar**

---

#### ❌ TEST 13: Intentar Eliminar Préstamo (DELETE) - DEBE FALLAR

**Endpoint:** `DELETE {{BASE_URL}}/api/prestamos/1`

**Headers:**
```
Authorization: Bearer {{TOKEN_TECNICO}}
```

**Respuesta Esperada:** `403 Forbidden`
```json
{
  "error": true,
  "message": "Acceso denegado: no tienes permisos para esta ruta."
}
```

**✅ Este error es CORRECTO**

---

### 🟢 ROL: INSTRUCTOR (Solo CREATE + READ, SIN UPDATE ni DELETE)

#### ✅ TEST 14: Listar Categorías (GET)

**Endpoint:** `GET {{BASE_URL}}/api/categoria`

**Headers:**
```
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Respuesta Esperada:** `200 OK`
```json
[
  {
    "id": 1,
    "nom_categ": "Electrónica",
    "descripcion": "Equipos electrónicos"
  }
]
```

---

#### ✅ TEST 15: Crear Categoría (POST)

**Endpoint:** `POST {{BASE_URL}}/api/categoria`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Body (raw - JSON):**
```json
{
  "nom_categ": "Mobiliario",
  "descripcion": "Muebles de oficina"
}
```

**Respuesta Esperada:** `201 Created`
```json
{
  "mensaje": "Categoria creada exitosamente",
  "data": {
    "id": 2,
    "nom_categ": "Mobiliario",
    "descripcion": "Muebles de oficina"
  }
}
```

---

#### ✅ TEST 16: Listar Subcategorías (GET)

**Endpoint:** `GET {{BASE_URL}}/api/subcategoria`

**Headers:**
```
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Respuesta Esperada:** `200 OK`
```json
[
  {
    "id": 1,
    "nom_subcateg": "Computadores",
    "categoria": "Electrónica"
  }
]
```

---

#### ✅ TEST 17: Crear Subcategoría (POST)

**Endpoint:** `POST {{BASE_URL}}/api/subcategoria`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Body (raw - JSON):**
```json
{
  "nom_subcateg": "Sillas",
  "id_categoria": 2
}
```

**Respuesta Esperada:** `201 Created`
```json
{
  "mensaje": "Subcategoria creada exitosamente",
  "data": {
    "id": 2,
    "nom_subcateg": "Sillas",
    "categoria": "Mobiliario"
  }
}
```

---

#### ✅ TEST 18: Crear Solicitud (POST)

**Endpoint:** `POST {{BASE_URL}}/api/solicitudes`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Body (raw - JSON):**
```json
{
  "id_usuario": 1,
  "fecha_solicitud": "2025-10-29",
  "fecha_uso": "2025-11-01",
  "hora_inicio": "08:00:00",
  "hora_fin": "12:00:00",
  "estado": 1
}
```

**Respuesta Esperada:** `201 Created`
```json
{
  "mensaje": "Solicitud creada exitosamente",
  "data": {
    "id": 1,
    "usuario": "instructor",
    "fecha_solicitud": "2025-10-29",
    ...
  }
}
```

---

#### ❌ TEST 19: Intentar Actualizar Usuario (PUT) - DEBE FALLAR

**Endpoint:** `PUT {{BASE_URL}}/api/Usuarios/1`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Body (raw - JSON):**
```json
{
  "nombre": "Nombre Modificado"
}
```

**Respuesta Esperada:** `403 Forbidden`
```json
{
  "error": true,
  "message": "Acceso denegado: no tienes permisos para esta ruta."
}
```

**✅ Este error es CORRECTO - Instructor NO puede actualizar**

---

#### ❌ TEST 20: Intentar Actualizar Solicitud (PUT) - DEBE FALLAR

**Endpoint:** `PUT {{BASE_URL}}/api/solicitudes/1`

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Body (raw - JSON):**
```json
{
  "estado": 2
}
```

**Respuesta Esperada:** `403 Forbidden`
```json
{
  "error": true,
  "message": "Acceso denegado: no tienes permisos para esta ruta."
}
```

**✅ Este error es CORRECTO**

---

#### ❌ TEST 21: Intentar Eliminar Categoría (DELETE) - DEBE FALLAR

**Endpoint:** `DELETE {{BASE_URL}}/api/categoria/1`

**Headers:**
```
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Respuesta Esperada:** `403 Forbidden`
```json
{
  "error": true,
  "message": "Acceso denegado: no tienes permisos para esta ruta."
}
```

**✅ Este error es CORRECTO**

---

#### ❌ TEST 22: Intentar Eliminar Ticket (DELETE) - DEBE FALLAR

**Endpoint:** `DELETE {{BASE_URL}}/api/tickets/1`

**Headers:**
```
Authorization: Bearer {{TOKEN_INSTRUCTOR}}
```

**Respuesta Esperada:** `403 Forbidden`
```json
{
  "error": true,
  "message": "Acceso denegado: no tienes permisos para esta ruta."
}
```

**✅ Este error es CORRECTO**

---

## 📊 TABLA RESUMEN DE PRUEBAS

| # | Endpoint | Método | Admin | Tecnico | Instructor | Resultado Esperado |
|---|----------|--------|-------|---------|------------|-------------------|
| 1 | `/api/Usuarios` | GET | ✅ 200 | ✅ 200 | ✅ 200 | Lista usuarios |
| 2 | `/api/Usuarios` | POST | ✅ 201 | ✅ 201 | ✅ 201 | Crea usuario |
| 3 | `/api/Usuarios/{id}` | PUT | ✅ 200 | ✅ 200 | ❌ 403 | Actualiza usuario |
| 4 | `/api/Usuarios/{id}` | DELETE | ✅ 204 | ❌ 403 | ❌ 403 | Elimina usuario |
| 5 | `/api/tickets` | POST | ✅ 201 | ✅ 201 | ✅ 201 | Crea ticket |
| 6 | `/api/tickets` | GET | ✅ 200 | ✅ 200 | ✅ 200 | Lista tickets |
| 7 | `/api/tickets/{id}` | DELETE | ✅ 204 | ❌ 403 | ❌ 403 | Elimina ticket |
| 8 | `/api/elementos` | GET | ✅ 200 | ✅ 200 | ✅ 200 | Lista elementos |
| 9 | `/api/elementos` | POST | ✅ 201 | ✅ 201 | ✅ 201 | Crea elemento |
| 10 | `/api/elementos/{id}` | DELETE | ✅ 204 | ❌ 403 | ❌ 403 | Elimina elemento |
| 11 | `/api/prestamos` | POST | ✅ 201 | ✅ 201 | ✅ 201 | Crea préstamo |
| 12 | `/api/prestamos/{id}` | DELETE | ✅ 204 | ❌ 403 | ❌ 403 | Elimina préstamo |
| 13 | `/api/solicitudes/{id}` | PUT | ✅ 200 | ✅ 200 | ❌ 403 | Actualiza solicitud |
| 14 | `/api/solicitudes` | POST | ✅ 201 | ✅ 201 | ✅ 201 | Crea solicitud |
| 15 | `/api/solicitudes/{id}` | DELETE | ✅ 204 | ❌ 403 | ❌ 403 | Elimina solicitud |
| 16 | `/api/categoria` | GET | ✅ 200 | ✅ 200 | ✅ 200 | Lista categorías |
| 17 | `/api/categoria` | POST | ✅ 201 | ✅ 201 | ✅ 201 | Crea categoría |
| 18 | `/api/categoria/{id}` | DELETE | ✅ 204 | ❌ 403 | ❌ 403 | Elimina categoría |
| 19 | `/api/subcategoria` | GET | ✅ 200 | ✅ 200 | ✅ 200 | Lista subcategorías |
| 20 | `/api/subcategoria` | POST | ✅ 201 | ✅ 201 | ✅ 201 | Crea subcategoría |
| 21 | `/api/subcategoria/{id}` | DELETE | ✅ 204 | ❌ 403 | ❌ 403 | Elimina subcategoría |

**Total de pruebas:** 21+ casos de uso

---

## 🔍 RESPUESTAS ESPERADAS

### ✅ Respuestas Exitosas

#### 200 OK - Consulta exitosa
```json
{
  "id": 1,
  "campo": "valor"
}
```

#### 201 Created - Creación exitosa
```json
{
  "Mensaje": "Recurso creado exitosamente",
  "data": { ... }
}
```

#### 204 No Content - Eliminación exitosa
```
(Sin contenido en el body)
```

---

### ❌ Respuestas de Error

#### 401 Unauthorized - Token inválido o expirado
```json
{
  "error": "Unauthorized",
  "message": "Token inválido o expirado"
}
```

**Solución:** Hacer login nuevamente

---

#### 403 Forbidden - Sin permisos
```json
{
  "error": true,
  "message": "Acceso denegado: no tienes permisos para esta ruta."
}
```

**Esto es CORRECTO si el rol no tiene permiso**

---

#### 404 Not Found - Recurso no existe
```json
{
  "error": "Recurso no encontrado"
}
```

---

#### 409 Conflict - Conflicto (duplicado)
```json
{
  "error": "El usuario ya existe"
}
```

---

#### 500 Internal Server Error - Error del servidor
```json
{
  "errores2": "Error al crear el recurso",
  "detalle": "Mensaje de error técnico"
}
```

---

## 🧪 CASOS DE PRUEBA ADICIONALES

### TEST 23: Token Expirado

**Esperar 1 hora** o usar un token viejo

**Endpoint:** `GET {{BASE_URL}}/api/Usuarios`

**Headers:**
```
Authorization: Bearer <token_expirado>
```

**Respuesta Esperada:** `401 Unauthorized`

---

### TEST 24: Sin Token

**Endpoint:** `GET {{BASE_URL}}/api/tickets`

**Headers:**
```
(Sin Authorization)
```

**Respuesta Esperada:** `401 Unauthorized`

---

### TEST 25: Token Malformado

**Endpoint:** `GET {{BASE_URL}}/api/elementos`

**Headers:**
```
Authorization: Bearer token_invalido_123
```

**Respuesta Esperada:** `401 Unauthorized`

---

## 🛠️ TROUBLESHOOTING

### Problema: "401 Unauthorized" en todas las peticiones

**Solución:**
1. Verificar que el servidor esté corriendo en `http://localhost:8081`
2. Hacer login nuevamente
3. Verificar que el token se guardó en las variables de collection
4. Verificar que estás usando `Bearer {{TOKEN_ADMIN}}` (con espacio después de Bearer)

---

### Problema: "403 Forbidden" inesperado

**Verificar:**
1. ¿Estás usando el token correcto para ese rol?
2. ¿El endpoint requiere permisos que tu rol no tiene?
3. Revisar la documentación `ROLES_Y_PERMISOS.md`

---

### Problema: "Connection refused"

**Solución:**
```powershell
# Iniciar el servidor
cd Back_proyecto\springboot-Proyecto\trabajo
.\mvnw spring-boot:run
```

---

### Problema: "404 Not Found"

**Verificar:**
1. La URL está bien escrita: `http://localhost:8081` (puerto 8081, NO 8080)
2. El endpoint existe: `/api/Usuarios` (con U mayúscula)
3. El servidor está corriendo

---

## 📝 SCRIPTS ÚTILES PARA POSTMAN

### Auto-guardar tokens en Tests

Agregar en el tab **Tests** de cada login:

```javascript
// Para cualquier login exitoso
if (pm.response.code === 200) {
    var jsonData = pm.response.json();
    var rol = jsonData.rol;
    
    if (rol === "Administrador") {
        pm.collectionVariables.set("TOKEN_ADMIN", jsonData.token);
    } else if (rol === "Tecnico") {
        pm.collectionVariables.set("TOKEN_TECNICO", jsonData.token);
    } else if (rol === "Instructor") {
        pm.collectionVariables.set("TOKEN_INSTRUCTOR", jsonData.token);
    }
    
    console.log("Token " + rol + " guardado exitosamente");
}
```

---

### Validar respuestas automáticamente

```javascript
// Validar status 200 OK
pm.test("Status code es 200", function () {
    pm.response.to.have.status(200);
});

// Validar que retorna JSON
pm.test("Response es JSON", function () {
    pm.response.to.be.json;
});

// Validar que tiene un campo específico
pm.test("Tiene campo 'data'", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('data');
});

// Validar 403 Forbidden (para probar restricciones)
pm.test("Acceso denegado correctamente", function () {
    pm.response.to.have.status(403);
});
```

---

## 🎯 CHECKLIST DE VERIFICACIÓN

### ✅ Administrador
- [ ] Puede hacer GET a todos los endpoints
- [ ] Puede hacer POST a todos los endpoints
- [ ] Puede hacer PUT a todos los endpoints
- [ ] Puede hacer DELETE a todos los endpoints

### ✅ Tecnico
- [ ] Puede hacer GET a todos los endpoints
- [ ] Puede hacer POST a todos los endpoints
- [ ] Puede hacer PUT a endpoints permitidos
- [ ] NO puede hacer DELETE (debe dar 403)

### ✅ Instructor
- [ ] Puede hacer GET a todos los endpoints
- [ ] Puede hacer POST a todos los endpoints
- [ ] NO puede hacer PUT (debe dar 403)
- [ ] NO puede hacer DELETE (debe dar 403)

---

## 📚 RECURSOS ADICIONALES

- **Documento de Roles:** `ROLES_Y_PERMISOS.md`
- **Documentación JWT:** `JWT_SISTEMA.md`
- **Guía de Base de Datos:** `BASE_DATOS_COHERENCIA.md`

---

## 🎉 CONCLUSIÓN

Con este manual puedes:
1. ✅ Autenticarte con los 3 roles
2. ✅ Probar todos los permisos CRUD
3. ✅ Verificar que las restricciones funcionan correctamente
4. ✅ Automatizar pruebas con scripts de Postman

**Total de tests recomendados:** 25+

**Tiempo estimado de pruebas completas:** 30-45 minutos

---

**¡Buenas pruebas! 🚀**
