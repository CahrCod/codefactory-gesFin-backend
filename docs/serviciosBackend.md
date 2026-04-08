# Documentación de Servicios Backend - PruebaReservas

**Versión:** 1.0.0  
**Fecha:** Marzo 16, 2026  
**Base URL:** `http://localhost:8080/api`

---

## 📋 Tabla de Contenidos

1. [Autenticación de Usuarios](#autenticación-de-usuarios)
2. [Gestión de Espacios](#gestión-de-espacios)
3. [Gestión de Reservas](#gestión-de-reservas)
4. [Calendario de Disponibilidad](#calendario-de-disponibilidad)
5. [Datos de Muestra](#datos-de-muestra)
6. [Códigos de Error](#códigos-de-error)

---

## 🔐 Autenticación de Usuarios

### 1. Registrar Usuario

**Endpoint:** `POST /usuarios/registro`

**Descripción:**  
Registra un nuevo usuario en el sistema.

**Headers:**

```
Content-Type: application/json
```

**Parámetros de Entrada:**

```json
{
  "email": "string (único, obligatorio)",
  "nombre": "string (obligatorio)",
  "apellido": "string (obligatorio)",
  "password": "string (obligatorio)"
}
```

**Ejemplo de Solicitud:**

```bash
curl -X POST http://localhost:8080/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "email": "nuevo.usuario@example.com",
    "nombre": "Pedro",
    "apellido": "González",
    "password": "password123"
  }'
```

**Respuesta Exitosa (201 Created):**

```json
{
  "statusCode": 201,
  "message": "Usuario registrado exitosamente",
  "data": {
    "id": 4,
    "email": "nuevo.usuario@example.com",
    "nombre": "Pedro",
    "apellido": "González",
    "autenticado": false,
    "fechaRegistro": "2026-03-16T22:30:00"
  },
  "success": true
}
```

**Respuesta de Error (400 Bad Request):**

```json
{
  "statusCode": 400,
  "message": "El usuario con email nuevo.usuario@example.com ya existe",
  "success": false
}
```

---

### 2. Autenticar Usuario

**Endpoint:** `POST /usuarios/autenticar`

**Descripción:**  
Autentica un usuario y marca su sesión como activa.

**Headers:**

```
Content-Type: application/json
```

**Parámetros de Entrada:**

```json
{
  "email": "string (obligatorio)",
  "password": "string (obligatorio)"
}
```

**Ejemplo de Solicitud:**

```bash
curl -X POST http://localhost:8080/api/usuarios/autenticar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "juan.perez@example.com",
    "password": "password123"
  }'
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Autenticación exitosa",
  "data": {
    "id": 1,
    "email": "juan.perez@example.com",
    "nombre": "Juan",
    "apellido": "Pérez",
    "autenticado": true,
    "fechaRegistro": "2026-03-16T20:00:00"
  },
  "success": true
}
```

**Respuesta de Error (401 Unauthorized):**

```json
{
  "statusCode": 401,
  "message": "Contraseña inválida",
  "success": false
}
```

---

### 3. Obtener Información de Usuario

**Endpoint:** `GET /usuarios/{id}`

**Descripción:**  
Obtiene la información de un usuario específico.

**Parámetros de Ruta:**

- `id` (long, obligatorio): ID del usuario

**Ejemplo de Solicitud:**

```bash
curl -X GET http://localhost:8080/api/usuarios/1
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Usuario obtenido exitosamente",
  "data": {
    "id": 1,
    "email": "juan.perez@example.com",
    "nombre": "Juan",
    "apellido": "Pérez",
    "autenticado": true,
    "fechaRegistro": "2026-03-16T20:00:00"
  },
  "success": true
}
```

---

### 4. Listar Todos los Usuarios

**Endpoint:** `GET /usuarios`

**Descripción:**  
Obtiene la lista de todos los usuarios registrados en el sistema.

**Ejemplo de Solicitud:**

```bash
curl -X GET http://localhost:8080/api/usuarios
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Usuarios obtenidos exitosamente",
  "data": [
    {
      "id": 1,
      "email": "juan.perez@example.com",
      "nombre": "Juan",
      "apellido": "Pérez",
      "autenticado": true,
      "fechaRegistro": "2026-03-16T20:00:00"
    },
    {
      "id": 2,
      "email": "maria.garcia@example.com",
      "nombre": "María",
      "apellido": "García",
      "autenticado": true,
      "fechaRegistro": "2026-03-16T20:30:00"
    }
  ],
  "success": true
}
```

---

## 🏢 Gestión de Espacios

### 5. Crear Espacio

**Endpoint:** `POST /espacios`

**Descripción:**  
Crea un nuevo espacio común en el sistema.

**Headers:**

```
Content-Type: application/json
```

**Parámetros de Entrada:**

```json
{
  "nombre": "string (obligatorio)",
  "descripcion": "string (opcional)",
  "ubicacion": "string (obligatorio)",
  "capacidad": "integer (obligatorio)"
}
```

**Ejemplo de Solicitud:**

```bash
curl -X POST http://localhost:8080/api/espacios \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Sala de Videoconferencias",
    "descripcion": "Sala equipada con tecnología audiovisual",
    "ubicacion": "Piso 4 - Bloque C",
    "capacidad": 20
  }'
```

**Respuesta Exitosa (201 Created):**

```json
{
  "statusCode": 201,
  "message": "Espacio creado exitosamente",
  "data": {
    "id": 5,
    "nombre": "Sala de Videoconferencias",
    "descripcion": "Sala equipada con tecnología audiovisual",
    "ubicacion": "Piso 4 - Bloque C",
    "capacidad": 20,
    "estado": "DISPONIBLE",
    "fechaCreacion": "2026-03-16T22:35:00"
  },
  "success": true
}
```

---

### 6. Obtener Espacio

**Endpoint:** `GET /espacios/{id}`

**Descripción:**  
Obtiene la información de un espacio específico.

**Parámetros de Ruta:**

- `id` (long, obligatorio): ID del espacio

**Ejemplo de Solicitud:**

```bash
curl -X GET http://localhost:8080/api/espacios/1
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Espacio obtenido exitosamente",
  "data": {
    "id": 1,
    "nombre": "Sala de Juntas A",
    "descripcion": "Sala para reuniones pequeñas con capacidad para 8 personas",
    "ubicacion": "Piso 2 - Bloque A",
    "capacidad": 8,
    "estado": "DISPONIBLE",
    "fechaCreacion": "2026-03-16T19:00:00"
  },
  "success": true
}
```

---

### 7. Listar Todos los Espacios

**Endpoint:** `GET /espacios`

**Descripción:**  
Obtiene la lista de todos los espacios registrados en el sistema.

**Ejemplo de Solicitud:**

```bash
curl -X GET http://localhost:8080/api/espacios
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Espacios obtenidos exitosamente",
  "data": [
    {
      "id": 1,
      "nombre": "Sala de Juntas A",
      "descripcion": "Sala para reuniones pequeñas",
      "ubicacion": "Piso 2",
      "capacidad": 8,
      "estado": "DISPONIBLE",
      "fechaCreacion": "2026-03-16T19:00:00"
    },
    {
      "id": 2,
      "nombre": "Auditorio Principal",
      "descripcion": "Espacio grande para eventos",
      "ubicacion": "Piso 1",
      "capacidad": 50,
      "estado": "DISPONIBLE",
      "fechaCreacion": "2026-03-16T19:30:00"
    }
  ],
  "success": true
}
```

---

### 8. Listar Espacios Disponibles

**Endpoint:** `GET /espacios/disponibles`

**Descripción:**  
Obtiene la lista de espacios disponibles en el sistema.

**Ejemplo de Solicitud:**

```bash
curl -X GET http://localhost:8080/api/espacios/disponibles
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Espacios disponibles obtenidos exitosamente",
  "data": [
    {
      "id": 1,
      "nombre": "Sala de Juntas A",
      "estado": "DISPONIBLE",
      "capacidad": 8
    },
    {
      "id": 3,
      "nombre": "Cancha de Tenis",
      "estado": "DISPONIBLE",
      "capacidad": 4
    }
  ],
  "success": true
}
```

---

## 📅 Gestión de Reservas

### 9. Crear Reserva

**Endpoint:** `POST /reservas`

**Descripción:**  
Crea una nueva reserva para un espacio. **Requisito:** El usuario debe estar autenticado.

**Headers:**

```
Content-Type: application/json
```

**Parámetros de Entrada:**

```json
{
  "usuarioId": "long (obligatorio)",
  "espacioId": "long (obligatorio)",
  "fechaInicio": "string ISO-8601 (obligatorio)",
  "fechaFin": "string ISO-8601 (obligatorio)"
}
```

**Formato de Fecha:** `YYYY-MM-DDTHH:mm:ss` (Ejemplo: `2026-03-16T14:00:00`)

**Ejemplo de Solicitud:**

```bash
curl -X POST http://localhost:8080/api/reservas \
  -H "Content-Type: application/json" \
  -d '{
    "usuarioId": 1,
    "espacioId": 1,
    "fechaInicio": "2026-03-17T10:00:00",
    "fechaFin": "2026-03-17T11:00:00"
  }'
```

**Respuesta Exitosa (201 Created):**

```json
{
  "statusCode": 201,
  "message": "Reserva creada exitosamente",
  "data": {
    "id": 3,
    "usuarioId": 1,
    "usuarioNombre": "Juan Pérez",
    "espacioId": 1,
    "espacioNombre": "Sala de Juntas A",
    "fechaInicio": "2026-03-17T10:00:00",
    "fechaFin": "2026-03-17T11:00:00",
    "estado": "CONFIRMADA",
    "fechaCreacion": "2026-03-16T22:40:00"
  },
  "success": true
}
```

**Respuesta de Error - Usuario No Autenticado (400):**

```json
{
  "statusCode": 400,
  "message": "Usuario no autenticado. Debe iniciar sesión para hacer reservas",
  "success": false
}
```

**Respuesta de Error - Conflicto de Horarios (400):**

```json
{
  "statusCode": 400,
  "message": "El horario seleccionado ya está reservado",
  "success": false
}
```

---

### 10. Obtener Reserva

**Endpoint:** `GET /reservas/{id}`

**Descripción:**  
Obtiene la información de una reserva específica.

**Parámetros de Ruta:**

- `id` (long, obligatorio): ID de la reserva

**Ejemplo de Solicitud:**

```bash
curl -X GET http://localhost:8080/api/reservas/1
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Reserva obtenida exitosamente",
  "data": {
    "id": 1,
    "usuarioId": 1,
    "usuarioNombre": "Juan Pérez",
    "espacioId": 1,
    "espacioNombre": "Sala de Juntas A",
    "fechaInicio": "2026-03-16T10:00:00",
    "fechaFin": "2026-03-16T11:00:00",
    "estado": "CONFIRMADA",
    "fechaCreacion": "2026-03-16T09:00:00"
  },
  "success": true
}
```

---

### 11. Listar Reservas por Espacio

**Endpoint:** `GET /reservas/espacio/{espacioId}`

**Descripción:**  
Obtiene todas las reservas confirmadas de un espacio específico.

**Parámetros de Ruta:**

- `espacioId` (long, obligatorio): ID del espacio

**Ejemplo de Solicitud:**

```bash
curl -X GET http://localhost:8080/api/reservas/espacio/1
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Reservas obtenidas exitosamente",
  "data": [
    {
      "id": 1,
      "usuarioId": 1,
      "usuarioNombre": "Juan Pérez",
      "espacioId": 1,
      "espacioNombre": "Sala de Juntas A",
      "fechaInicio": "2026-03-16T10:00:00",
      "fechaFin": "2026-03-16T11:00:00",
      "estado": "CONFIRMADA",
      "fechaCreacion": "2026-03-16T09:00:00"
    },
    {
      "id": 2,
      "usuarioId": 1,
      "usuarioNombre": "Juan Pérez",
      "espacioId": 1,
      "espacioNombre": "Sala de Juntas A",
      "fechaInicio": "2026-03-16T14:00:00",
      "fechaFin": "2026-03-16T15:00:00",
      "estado": "CONFIRMADA",
      "fechaCreacion": "2026-03-16T09:15:00"
    }
  ],
  "success": true
}
```

---

### 12. Listar Reservas por Usuario

**Endpoint:** `GET /reservas/usuario/{usuarioId}`

**Descripción:**  
Obtiene todas las reservas confirmadas de un usuario específico.

**Parámetros de Ruta:**

- `usuarioId` (long, obligatorio): ID del usuario

**Ejemplo de Solicitud:**

```bash
curl -X GET http://localhost:8080/api/reservas/usuario/1
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Reservas obtenidas exitosamente",
  "data": [
    {
      "id": 1,
      "usuarioId": 1,
      "usuarioNombre": "Juan Pérez",
      "espacioId": 1,
      "espacioNombre": "Sala de Juntas A",
      "fechaInicio": "2026-03-16T10:00:00",
      "fechaFin": "2026-03-16T11:00:00",
      "estado": "CONFIRMADA",
      "fechaCreacion": "2026-03-16T09:00:00"
    }
  ],
  "success": true
}
```

---

### 13. Cancelar Reserva

**Endpoint:** `PUT /reservas/{id}/cancelar`

**Descripción:**  
Cancela una reserva existente.

**Parámetros de Ruta:**

- `id` (long, obligatorio): ID de la reserva

**Ejemplo de Solicitud:**

```bash
curl -X PUT http://localhost:8080/api/reservas/1/cancelar
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Reserva cancelada exitosamente",
  "data": {
    "id": 1,
    "usuarioId": 1,
    "usuarioNombre": "Juan Pérez",
    "espacioId": 1,
    "espacioNombre": "Sala de Juntas A",
    "fechaInicio": "2026-03-16T10:00:00",
    "fechaFin": "2026-03-16T11:00:00",
    "estado": "CANCELADA",
    "fechaCreacion": "2026-03-16T09:00:00"
  },
  "success": true
}
```

---

## 📆 Calendario de Disponibilidad

### 14. Obtener Calendario del Día

**Endpoint:** `GET /calendario/dia/{espacioId}`

**Descripción:**  
Obtiene el calendario de disponibilidad de un espacio para un día específico, mostrando franjas horarias de 1 hora indicando si están disponibles u ocupadas. **Requisito:** El usuario debe estar autenticado.

**Parámetros de Ruta:**

- `espacioId` (long, obligatorio): ID del espacio

**Parámetros de Consulta:**

- `fecha` (string, opcional): Fecha en formato `YYYY-MM-DD`. Si no se proporciona, se usa la fecha actual.

**Ejemplo de Solicitud:**

```bash
# Para hoy
curl -X GET http://localhost:8080/api/calendario/dia/1

# Para una fecha específica
curl -X GET http://localhost:8080/api/calendario/dia/1?fecha=2026-03-20
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Calendario del día obtenido exitosamente",
  "data": {
    "espacioId": 1,
    "espacioNombre": "Sala de Juntas A",
    "fecha": "2026-03-16",
    "franjas": [
      {
        "horaInicio": "2026-03-16T00:00:00",
        "horaFin": "2026-03-16T01:00:00",
        "estado": "DISPONIBLE",
        "reservaId": null
      },
      {
        "horaInicio": "2026-03-16T10:00:00",
        "horaFin": "2026-03-16T11:00:00",
        "estado": "OCUPADO",
        "reservaId": 1
      },
      {
        "horaInicio": "2026-03-16T11:00:00",
        "horaFin": "2026-03-16T12:00:00",
        "estado": "DISPONIBLE",
        "reservaId": null
      },
      {
        "horaInicio": "2026-03-16T14:00:00",
        "horaFin": "2026-03-16T15:00:00",
        "estado": "OCUPADO",
        "reservaId": 2
      }
    ],
    "totalDisponibles": 22,
    "totalOcupados": 2,
    "tieneDisponibilidad": true
  },
  "success": true
}
```

**Estructura de Franjas:**

- `horaInicio`: Hora de inicio de la franja
- `horaFin`: Hora de fin de la franja
- `estado`: `"DISPONIBLE"` o `"OCUPADO"`
- `reservaId`: ID de la reserva si la franja está ocupada, `null` si está disponible

---

### 15. Obtener Calendario de la Semana

**Endpoint:** `GET /calendario/semana/{espacioId}`

**Descripción:**  
Obtiene el calendario de disponibilidad de un espacio para una semana completa (7 días). **Requisito:** El usuario debe estar autenticado.

**Parámetros de Ruta:**

- `espacioId` (long, obligatorio): ID del espacio

**Parámetros de Consulta:**

- `fechaInicio` (string, opcional): Fecha de inicio en formato `YYYY-MM-DD`. Si no se proporciona, se usa la fecha actual.

**Ejemplo de Solicitud:**

```bash
# Para la semana actual
curl -X GET http://localhost:8080/api/calendario/semana/1

# Para una semana específica
curl -X GET http://localhost:8080/api/calendario/semana/1?fechaInicio=2026-03-20
```

**Respuesta Exitosa (200 OK):**

```json
{
  "statusCode": 200,
  "message": "Calendario de la semana obtenido exitosamente",
  "data": {
    "espacioId": 1,
    "espacioNombre": "Sala de Juntas A",
    "fecha": "2026-03-16",
    "franjas": [
      {
        "horaInicio": "2026-03-16T00:00:00",
        "horaFin": "2026-03-16T01:00:00",
        "estado": "DISPONIBLE",
        "reservaId": null
      },
      {
        "horaInicio": "2026-03-16T10:00:00",
        "horaFin": "2026-03-16T11:00:00",
        "estado": "OCUPADO",
        "reservaId": 1
      }
    ],
    "totalDisponibles": 166,
    "totalOcupados": 2,
    "tieneDisponibilidad": true
  },
  "success": true
}
```

---

## Datos de Muestra

La siguiente tabla muestra los datos de muestra que se insertan automáticamente en la base de datos al iniciar el sistema:

### Usuarios

| ID  | Email                    | Nombre | Apellido | Contraseña  | Autenticado |
| --- | ------------------------ | ------ | -------- | ----------- | ----------- |
| 1   | juan.perez@example.com   | Juan   | Pérez    | password123 | Sí          |
| 2   | maria.garcia@example.com | María  | García   | password456 | Sí          |
| 3   | carlos.lopez@example.com | Carlos | López    | password789 | No          |

### Espacios

| ID  | Nombre              | Descripción                  | Ubicación         | Capacidad | Estado     |
| --- | ------------------- | ---------------------------- | ----------------- | --------- | ---------- |
| 1   | Sala de Juntas A    | Sala para reuniones pequeñas | Piso 2 - Bloque A | 8         | DISPONIBLE |
| 2   | Sala de Juntas B    | Sala para reuniones medianas | Piso 3 - Bloque B | 15        | DISPONIBLE |
| 3   | Auditorio Principal | Espacio grande para eventos  | Piso 1 - Entrada  | 50        | DISPONIBLE |
| 4   | Cancha de Tenis     | Cancha de tenis cubierta     | Zona deportiva    | 4         | DISPONIBLE |

### Reservas

| ID  | Usuario   | Espacio    | Fecha Inicio | Fecha Fin    | Estado     |
| --- | --------- | ---------- | ------------ | ------------ | ---------- |
| 1   | Juan (1)  | Sala A (1) | Hoy 10:00    | Hoy 11:00    | CONFIRMADA |
| 2   | Juan (1)  | Sala A (1) | Hoy 14:00    | Hoy 15:00    | CONFIRMADA |
| 3   | María (2) | Sala B (2) | Mañana 09:00 | Mañana 12:00 | CONFIRMADA |

## Códigos de Error

| Código | Descripción           | Causa                                              |
| ------ | --------------------- | -------------------------------------------------- |
| 200    | OK                    | Solicitud exitosa                                  |
| 201    | Created               | Recurso creado exitosamente                        |
| 400    | Bad Request           | Parámetros inválidos o conflicto de horarios       |
| 401    | Unauthorized          | Usuario no autenticado (requerido para calendario) |
| 404    | Not Found             | Recurso no encontrado                              |
| 500    | Internal Server Error | Error del servidor                                 |

---

## 🧪 Pruebas

### Ejemplo Completo de Flujo

```bash
# 1. Registrar usuario
curl -X POST http://localhost:8080/api/usuarios/registro \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "nombre": "Test",
    "apellido": "User",
    "password": "test123"
  }'

# 2. Autenticar usuario
curl -X POST http://localhost:8080/api/usuarios/autenticar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "test123"
  }'

# 3. Listar espacios disponibles
curl -X GET http://localhost:8080/api/espacios/disponibles

# 4. Ver calendario del día para el espacio 1
curl -X GET http://localhost:8080/api/calendario/dia/1

# 5. Crear reserva
curl -X POST http://localhost:8080/api/reservas \
  -H "Content-Type: application/json" \
  -d '{
    "usuarioId": 4,
    "espacioId": 1,
    "fechaInicio": "2026-03-18T15:00:00",
    "fechaFin": "2026-03-18T16:00:00"
  }'

# 6. Ver calendario actualizado
curl -X GET http://localhost:8080/api/calendario/dia/1?fecha=2026-03-18
```

---

**Fin de Documentación**
