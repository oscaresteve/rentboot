-- =========================
-- TABLA CLIENTE
-- =========================
CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- TABLA CATEGORIA
-- =========================
CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- =========================
-- TABLA VEHICULO
-- =========================
CREATE TABLE vehiculo (
    id BIGSERIAL PRIMARY KEY,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    precio_por_dia NUMERIC(10,2) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    categoria_id BIGINT NOT NULL,
    CONSTRAINT fk_vehiculo_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id)
        ON DELETE RESTRICT
);

-- =========================
-- TABLA ALQUILER
-- =========================
CREATE TABLE alquiler (
    id BIGSERIAL PRIMARY KEY,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    precio_total NUMERIC(10,2) NOT NULL,
    cliente_id BIGINT NOT NULL,
    vehiculo_id BIGINT NOT NULL,
    CONSTRAINT fk_alquiler_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_alquiler_vehiculo
        FOREIGN KEY (vehiculo_id)
        REFERENCES vehiculo(id)
        ON DELETE RESTRICT
);

-- =========================
-- TABLA ROL
-- =========================
CREATE TABLE rol (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- =========================
-- TABLA USUARIO
-- =========================
CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

-- =========================
-- TABLA USUARIO_ROL (N:M)
-- =========================
CREATE TABLE usuario_rol (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    CONSTRAINT fk_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_rol
        FOREIGN KEY (rol_id)
        REFERENCES rol(id)
        ON DELETE CASCADE
);
