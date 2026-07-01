# MS-Subastas

Microservicio encargado de gestionar las subastas de productos pesqueros en la Caleta Lo Abarca.

## Tecnologías
- Java 21
- Spring Boot 4.0.6
- PostgreSQL (Neon)
- Maven

## Puerto
`8083`

## Endpoints

### Subastas
| Método | URL | Descripción |
|--------|-----|-------------|
| POST | /api/subastas | Crear nueva subasta |
| GET | /api/subastas | Obtener todas las subastas |
| GET | /api/subastas/{id} | Obtener subasta por ID |
| GET | /api/subastas/estado/{estado} | Obtener subastas por estado (ABIERTA, CERRADA, DESIERTA) |
| GET | /api/subastas/pescador/{pescadorId} | Obtener subastas por pescador |
| PUT | /api/subastas/{id}/cerrar | Cerrar subasta con precio final |
| PUT | /api/subastas/{id}/desierta | Marcar subasta como desierta |

## Cómo correr el proyecto

1. Clonar el repositorio
```bash
git clone https://github.com/Malos-pal-catre/ms-subastas.git
```

2. Entrar a la carpeta
```bash
cd ms-subastas
```

3. Correr el proyecto
```bash
./mvnw spring-boot:run
```

## Modelo de datos

**Subasta**
- id, especie, kilos, pescadorId, precioBase, precioFinal, compradorGanadorId, estado, fechaInicio, fechaCierre