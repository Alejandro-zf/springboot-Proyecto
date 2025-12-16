# Estructura del backend y cómo se relaciona todo

Este documento describe la organización general del backend del proyecto y la relación entre sus componentes principales. Está pensado para desarrolladores que se integren al proyecto o necesiten entender el flujo de datos y responsabilidades.

---

## Visión general del repositorio

- `pom.xml`, `mvnw`, `mvnw.cmd`: archivos de construcción Maven y wrappers.
- `src/`
  - `main/`
    - `java/`: código fuente Java (controladores, servicios, repositorios, entidades, seguridad, utilidades).
    - `resources/`: archivos de configuración (por ejemplo `application.properties`), plantillas, y otros recursos.
  - `test/`: pruebas unitarias e integradas.
- `target/`: salida de compilación y artefactos generados (no versionado normalmente).
- `uploads/`: carpeta para archivos subidos en runtime, con subcarpetas como `espacios/` y `tickets/`.
- Documentación y ayudas dentro de `trabajo/` (por ejemplo `GUIA_RAPIDA_API.md`, `JWT_ANALYSIS_REPORT.md`, `POSTMAN_MANUAL.md`).

---

## Capas y responsabilidades

1. Controllers (capa web / endpoints)
   - Exponen la API REST (endpoints HTTP) que consume el frontend.
   - Validan entrada básica, transforman DTOs y delegan la lógica a servicios.

2. Services (lógica de negocio)
   - Implementan reglas de negocio, orquestan llamadas a repositorios y otros servicios.
   - Gestionan transacciones cuando procede.

3. Repositories (acceso a datos)
   - Interfaces (habitualmente Spring Data JPA) para CRUD sobre la base de datos.
   - Se ocupan de consultas, paginación y mapeo entidad-tabla.

4. Entities / Models
   - Clases que representan tablas en la base de datos (anotadas con JPA/Hibernate).
   - Incluyen relaciones (`@OneToMany`, `@ManyToOne`, etc.) y restricciones.

5. DTOs (Data Transfer Objects)
   - Objetos usados para comunicación entre cliente y servidor o entre capas, evitando exponer entidades directamente.

6. Seguridad
   - Mecanismo de autenticación basado en JWT (ver `JWT_ANALYSIS_REPORT.md`).
   - Filtros/interceptores que validan el token en cada petición y colocan el `Authentication` en el contexto de seguridad.
   - Configuración de roles y permisos (ver `ROLES_Y_PERMISOS.md`) para controlar acceso a endpoints.

7. Recursos estáticos & uploads
   - `resources/` para configuraciones y assets empaquetados.
   - `uploads/` para archivos subidos (p.ej. imágenes de tickets, planos de espacios). Los servicios que manejan archivos suelen guardar la ruta en la BD y el binario en disco.

---

## Flujo típico de una petición (resumen)

1. El cliente (frontend móvil o web) hace una petición HTTP a un endpoint.
2. El `Controller` recibe la petición y realiza validaciones mínimas.
3. El `Controller` construye o recibe un DTO y llama al `Service` correspondiente.
4. El `Service` ejecuta la lógica: valida reglas de negocio, transforma datos, y llama a `Repository` para persistir/consultar.
5. El `Repository` interactúa con la base de datos usando JPA/Hibernate.
6. El `Service` devuelve resultados al `Controller` que mapea la respuesta a DTOs adecuados.
7. El `Controller` responde al cliente con código HTTP y payload JSON.

En solicitudes autenticadas, entre el paso 1 y 2 existe un filtro de seguridad que verifica el JWT y carga el usuario en el contexto.

---

## Integración con archivos y sub-sistemas

- Almacenamiento de archivos: los endpoints que gestionan uploads guardan archivos en `uploads/` y persisten rutas/metadatos en la BD.
- Configuración: `src/main/resources/application.properties` (o `application.yml`) contiene parámetros de conexión a BD, puerto, configuración de JWT, rutas de uploads, etc.
- Pruebas: `src/test/` contiene pruebas unitarias e integradas que validan servicios y controladores.
- Documentación: dentro de `trabajo/` hay guías y análisis que describen la API (puede incluir colecciones Postman, matrices de permisos, y análisis de seguridad).

---

## Notas sobre seguridad y autenticación

- Login: el endpoint de autenticación valida credenciales y genera un JWT con roles/claims.
- Token: el JWT se envía en el header `Authorization: Bearer <token>` para solicitudes posteriores.
- Validación: un filtro (o componente de seguridad) valida el token y carga las credenciales en el `SecurityContext`.
- Permisos: los controladores o métodos están protegidos por roles/authorities (p. ej. `@PreAuthorize` o configuración de rutas).

---

## Sugerencias rápidas para navegar el código

- Buscar controladores: dentro de `src/main/java` busca paquetes `controller` o `web`.
- Lógica de negocio: paquetes `service` o `business`.
- Acceso a datos: paquetes `repository` o `dao`.
- Entidades: paquetes `model` o `entity`.
- Seguridad: paquetes `security`, `auth`, o `jwt`.

---

## Ejemplo de relación entre capas (pseudo-diagrama)

Cliente -> `Controller` -> `Service` -> `Repository` -> Base de datos

Y para la seguridad: Cliente (token) -> Filtro JWT -> `Controller` (con `Authentication`)

---

## Archivos importantes a revisar

- `pom.xml` : dependencias y plugins.
- `src/main/resources/application.properties` : configuración principal.
- `uploads/` : directorio de archivos subidos en runtime.
- Documentos en `trabajo/` para detalles sobre API y seguridad.

---

Si quieres, puedo:

- Añadir ejemplos concretos de endpoints (ruta, método, request/response) leyendo los `Controllers` del proyecto.
- Mapear paquetes y archivos reales indicando rutas de archivo precisas.

Fin del documento.
