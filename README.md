# ruta-servicio

Microservicio de gestión de rutas para la plataforma Karübag.

## Descripción
Gestiona las rutas diarias de los operadores de retiro. Permite programar, iniciar y completar rutas con múltiples paradas.

## Tecnologías
- Java 21
- Spring Boot 3.5.14
- Spring Data JPA
- PostgreSQL (Neon)

## Puerto
`8087`

## Base de datos
`karubag_ruta`

## Endpoints principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /api/rutas | Listar todas las rutas |
| GET | /api/rutas/operador/{operadorId} | Listar por operador |
| GET | /api/rutas/operador/{operadorId}/hoy | Obtener ruta del día |
| GET | /api/rutas/fecha/{fecha} | Listar por fecha |
| GET | /api/rutas/estado/{estado} | Listar por estado |
| GET | /api/rutas/{id} | Obtener ruta por ID |
| POST | /api/rutas | Crear ruta |
| PUT | /api/rutas/{id} | Actualizar ruta |
| PUT | /api/rutas/{id}/iniciar | Iniciar ruta |
| PUT | /api/rutas/{id}/completar | Completar ruta |
| DELETE | /api/rutas/{id} | Eliminar ruta |

## Estados de ruta
`PROGRAMADA`, `EN_CURSO`, `COMPLETADA`, `CANCELADA`

## Cómo ejecutar
```bash
./mvnw spring-boot:run
```

## Variables de entorno
```
spring.datasource.url=jdbc:postgresql://<host>/karubag_ruta
spring.datasource.username=<usuario>
spring.datasource.password=<contraseña>
```