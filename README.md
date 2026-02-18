# RentBoot

API REST para gestion de alquiler de vehiculos, construida con Spring Boot, PostgreSQL y seguridad JWT.

## 1. Stack Tecnico

- Java 21
- Spring Boot 3.5.10
- Spring Web
- Spring Data JPA
- Spring Security (JWT)
- Spring Validation
- Springdoc OpenAPI (Swagger UI)
- MapStruct
- Lombok
- PostgreSQL 16 (Docker)
- Maven

## 2. Estructura del Proyecto

Ruta base del codigo:

- `src/main/java/com/oscaresteve/rentboot`

Capas principales:

- `controller`: endpoints REST
- `srv` y `srv/impl`: logica de negocio
- `repository`: acceso a datos
- `model/db`: entidades JPA
- `model/dto`: DTOs de entrada/salida
- `srv/mapper`: mapeos MapStruct
- `security`: JWT, filtros y configuracion de seguridad
- `exception`: manejo global de errores

Recursos:

- `src/main/resources/application.properties`
- `src/main/resources/META-INF/additional-spring-configuration-metadata.json`

SQL:

- `database/create_database.sql`
- `database/create_tables.sql`
- `database/insert_data.sql`

Coleccion de pruebas HTTP:

- `http/*.http`

## 3. Modelo de Datos

Tablas principales:

- `cliente`
- `categoria`
- `vehiculo`
- `alquiler`
- `usuario`
- `rol`
- `usuario_rol`

Relaciones:

- `categoria (1) -> (N) vehiculo`
- `cliente (1) -> (N) alquiler`
- `vehiculo (1) -> (N) alquiler`
- `usuario (N) <-> (M) rol` por `usuario_rol`

## 4. Configuracion

Archivo: `src/main/resources/application.properties`

Valores actuales relevantes:

- `server.port=8090`
- `spring.datasource.url=jdbc:postgresql://localhost:5432/rentboot`
- `spring.datasource.username=postgres`
- `spring.datasource.password=root`
- `spring.jpa.hibernate.ddl-auto=validate`
- `springdoc.api-docs.path=/api-docs`
- `springdoc.swagger-ui.path=/swagger-rentboot-ui`
- `jwt.expiration=3600000`

Nota: el `docker-compose.yml` usa por defecto credenciales `rentboot / rentboot2026`. Si usas Docker tal cual, ajusta `application.properties` o cambia las variables del contenedor para que coincidan.

## 5. Arranque Rapido

### Opcion A: Base de datos en Docker

1. Levantar PostgreSQL:

```bash
docker compose up -d
```

2. Crear esquema y datos (si no usas auto-init):

```bash
psql -h localhost -U rentboot -d rentboot -f database/create_tables.sql
psql -h localhost -U rentboot -d rentboot -f database/insert_data.sql
```

### Opcion B: Base de datos local ya existente

1. Crear base de datos `rentboot`.
2. Ejecutar:

```bash
psql -h localhost -U <usuario> -d rentboot -f database/create_tables.sql
psql -h localhost -U <usuario> -d rentboot -f database/insert_data.sql
```

## 6. Ejecucion de la API

Con Maven Wrapper:

```bash
./mvnw spring-boot:run
```

En Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Compilar WAR:

```bash
./mvnw clean package
```

Salida:

- `target/rentboot-0.0.1-SNAPSHOT.war`

## 7. Documentacion OpenAPI

- Swagger UI: `http://localhost:8090/swagger-rentboot-ui`
- OpenAPI JSON: `http://localhost:8090/api-docs`

Configuracion en:

- `src/main/resources/application.properties`
- `src/main/java/com/oscaresteve/rentboot/swagger/OpenApiConfig.java`

## 8. Seguridad y Autenticacion

Login y registro:

- `POST /api/auth/login`
- `POST /api/auth/register`

Cabecera para endpoints protegidos:

```http
Authorization: Bearer <jwt>
```

Reglas principales:

- Publico: `/api/auth/**`, Swagger y docs OpenAPI
- Solo `ADMIN`: `/api/usuarios/**`, `/api/roles/**`
- `GET` de catalogos y alquileres: `USER` o `ADMIN`
- `POST` de alquileres: `USER` o `ADMIN`
- `PUT/DELETE` de alquileres: `ADMIN`
- `POST/PUT/DELETE` de categorias, vehiculos y clientes: `ADMIN`

## 9. Datos Iniciales

El script `database/insert_data.sql` carga datos de prueba realistas para validar:

- CRUD completo de categorias, clientes, vehiculos, alquileres
- Filtros de alquiler por cliente/vehiculo
- Estadisticas (`/api/alquileres/stats/*`)
- Seguridad por rol

Usuarios iniciales:

- Admin: `admin`
- User: `user.demo`
- Inactivo: `operador.inactivo`

Contrasenas:

- `Admin12345!`
- `User12345!`
- `Operador123!`

## 10. Endpoints Base

- `/api/auth`
- `/api/usuarios`
- `/api/roles`
- `/api/categorias`
- `/api/clientes`
- `/api/vehiculos`
- `/api/alquileres`

## 11. Pruebas Manuales

Para probar rapido desde VS Code:

- `http/auth-tests.http`
- `http/security-tests.http`
- `http/rol-tests.http`
- `http/usuario-tests.http`
- `http/categoria-tests.http`
- `http/cliente-tests.http`
- `http/vehiculo-tests.http`
- `http/alquiler-tests.http`
