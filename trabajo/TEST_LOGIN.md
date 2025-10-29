# 🔐 Guía de Autenticación - Tech Inventory

## 📋 Usuarios Predeterminados Creados

| Rol | Email | Password | Documento |
|-----|-------|----------|-----------|
| **Instructor** | instructor@tech.com | instructor123 | 1234567890 |
| **Administrador** | admin@tech.com | admin123 | 9876543210 |
| **Técnico** | tecnico@tech.com | tecnico123 | 5555555555 |

---

## 🚀 Cómo Obtener el Token JWT

### **Opción 1: Ver token en la consola al iniciar**
Al iniciar la aplicación, busca en la consola:
```
🔑 Token para 'instructor':
   Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWI...
   Credenciales -> Usuario: instructor | Password: instructor123
```

### **Opción 2: Hacer Login via API**

**Endpoint:** `POST http://localhost:8081/auth/login`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "username": "instructor@tech.com",
  "password": "instructor123"
}
```

**Respuesta exitosa:**
```json
{
  "token": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpbnN0cnVjdG9yQHRlY2guY29tIiwiaWF0IjoxNzMwMTQyMzQ1LCJleHAiOjE3MzAxNDU5NDV9.abc123...",
  "refreshToken": null,
  "accessToken": "Bearer eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## 📝 Ejemplos con diferentes usuarios

### **Login como Instructor:**
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "instructor@tech.com",
    "password": "instructor123"
  }'
```

### **Login como Administrador:**
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin@tech.com",
    "password": "admin123"
  }'
```

### **Login como Técnico:**
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "tecnico@tech.com",
    "password": "tecnico123"
  }'
```

---

## 🔑 Cómo Usar el Token

Una vez obtengas el token, úsalo en el header `Authorization` de tus peticiones:

**Ejemplo:**
```bash
curl -X GET http://localhost:8081/api/elementos \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWI..."
```

**En Postman/Insomnia:**
1. Ve a la pestaña **Headers**
2. Agrega un nuevo header:
   - **Key:** `Authorization`
   - **Value:** `Bearer <tu-token-aquí>`

---

## ⚠️ Notas Importantes

1. **El token expira en 1 hora** (3600 segundos)
2. **El username para login es el EMAIL**, no el nombre de usuario
3. **El token NO se guarda en la base de datos** (es stateless)
4. **Cada vez que hagas login obtendrás un token nuevo**

---

## 🧪 Prueba con PowerShell (Windows)

```powershell
# Login como Instructor
Invoke-RestMethod -Uri "http://localhost:8081/auth/login" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"username":"instructor@tech.com","password":"instructor123"}'
```

```powershell
# Login como Administrador
Invoke-RestMethod -Uri "http://localhost:8081/auth/login" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"username":"admin@tech.com","password":"admin123"}'
```

```powershell
# Login como Técnico
Invoke-RestMethod -Uri "http://localhost:8081/auth/login" `
  -Method Post `
  -ContentType "application/json" `
  -Body '{"username":"tecnico@tech.com","password":"tecnico123"}'
```

---

## ✅ Roles Asignados

Cada usuario tiene su rol correspondiente en el sistema:

- **instructor@tech.com** → `ROLE_INSTRUCTOR`
- **admin@tech.com** → `ROLE_ADMINISTRADOR`
- **tecnico@tech.com** → `ROLE_TECNICO`

Estos roles se pueden usar para proteger endpoints específicos en tu aplicación.
