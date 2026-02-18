🚗 RentBoot

API REST desarrollada con Spring Boot para la gestión de alquiler de vehículos.

Proyecto académico orientado a la aplicación de los conceptos avanzados vistos en la segunda evaluación:

- Arquitectura en capas
- Principios SOLID
- DTOs y mapeo
- Gestión global de excepciones
- Paginación, filtrado y ordenación
- Seguridad con JWT
- Documentación con Swagger/OpenAPI
- Base de datos PostgreSQL en Docker

📌 Información General

- Nombre del proyecto: RentBoot
- Empaquetado: WAR
- Puerto de ejecución: 8090
- Base de datos: PostgreSQL
- Gestión de dependencias: Maven

🛠 Tecnologías Utilizadas

- Java 21
- Spring Boot 3.5.x
- Spring Web
- Spring Data JPA
- PostgreSQL
- Docker
- Lombok
- Spring Validation
- Spring Security (JWT)
- Springdoc OpenAPI (Swagger)

🏗 Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas:

    Controller → Service → Repository

Separación en paquetes:

- config
- controller
- service
- service.impl
- repository
- entity
- dto
- mapper
- exception
- security

Aplicando principios SOLID y separación de responsabilidades.

🐳 Base de Datos (PostgreSQL con Docker)

Requisitos:

- Docker instalado
- Docker Compose habilitado

Levantar el contenedor

Desde la raíz del proyecto:

    docker compose up -d

Configuración del contenedor

- Base de datos: rentboot
- Usuario: rentboot
- Contraseña: rentboot2026
- Puerto: 5432

🗄 Scripts SQL

Ubicación:

    /database

Archivos incluidos:

- create_database.sql
- create_tables.sql
- insert_data.sql

Estos scripts permiten crear manualmente la base de datos y todas las tablas necesarias para el funcionamiento de la aplicación.

📊 Modelo Relacional

Relaciones principales del sistema:

Cliente (1) ──── (N) Alquiler (N) ──── (1) Vehiculo (N) ──── (1) Categoria
Usuario (N) ──── (M) Rol

Tablas principales

- cliente
- categoria
- vehiculo
- alquiler
- usuario
- rol
- usuario_rol

⚙ Configuración de la Aplicación

Archivo:

    src/main/resources/application.properties

Puerto configurado:

    server.port=8090

Hibernate configurado para validar el esquema:

    spring.jpa.hibernate.ddl-auto=validate

📦 Generar el WAR

Para generar el archivo WAR:

    mvn clean package

Archivo generado:

    target/rentboot.war

Puede ejecutarse como aplicación standalone o desplegarse en un servidor externo (ej. Apache Tomcat).

📄 Documentación Swagger

La documentación OpenAPI está integrada con Springdoc.

Una vez ejecutada la aplicación en el puerto `8090`, puedes acceder a:

    http://localhost:8090/swagger-rentboot-ui

Especificación OpenAPI (JSON):

    http://localhost:8090/api-docs

Configuración principal en:

    src/main/resources/application.properties

Propiedades usadas:

    springdoc.swagger-ui.enabled=true
    springdoc.api-docs.enabled=true
    springdoc.api-docs.path=/api-docs
    springdoc.swagger-ui.path=/swagger-rentboot-ui

Metadatos globales de la API (título, versión, contacto, servidor):

    src/main/java/com/oscaresteve/rentboot/swagger/OpenApiConfig.java

🔐 Seguridad

La API implementará autenticación basada en JWT para proteger determinados recursos.

Se incluirá:

- Endpoint de login
- Protección de endpoints específicos
- Configuración de Swagger con autenticación Bearer

🧪 Pruebas

Todas las funcionalidades implementadas estarán documentadas en un fichero:

    rentboot-tests.http

    ⚠️ Solo se evaluarán las funcionalidades incluidas en este fichero.
