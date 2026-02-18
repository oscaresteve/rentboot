BEGIN;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

TRUNCATE TABLE
  usuario_rol,
  alquiler,
  vehiculo,
  cliente,
  categoria,
  usuario,
  rol
RESTART IDENTITY CASCADE;

-- =========================
-- ROLES
-- =========================
INSERT INTO rol (id, nombre) VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_USER');

-- =========================
-- USUARIOS (password hash BCrypt via pgcrypto)
-- Credenciales:
--   admin / Admin12345!
--   user.demo / User12345!
-- =========================
INSERT INTO usuario (id, username, password, enabled) VALUES
  (1, 'admin', crypt('Admin12345!', gen_salt('bf', 10)), TRUE),
  (2, 'user.demo', crypt('User12345!', gen_salt('bf', 10)), TRUE),
  (3, 'supervisor', crypt('Supervisor123!', gen_salt('bf', 10)), TRUE),
  (4, 'operador.inactivo', crypt('Operador123!', gen_salt('bf', 10)), FALSE);

INSERT INTO usuario_rol (usuario_id, rol_id) VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (3, 2),
  (4, 2);

-- =========================
-- CATEGORIAS
-- =========================
INSERT INTO categoria (id, nombre, descripcion) VALUES
  (1, 'Economico', 'Vehiculos de bajo consumo para ciudad'),
  (2, 'Compacto', 'Vehiculos pequenos para uso diario'),
  (3, 'SUV', 'Vehiculos deportivos utilitarios'),
  (4, 'Electrico', 'Vehiculos electricos de cero emisiones'),
  (5, 'Familiar', 'Vehiculos amplios para viajes en grupo'),
  (6, 'Premium', 'Vehiculos de gama alta');

-- =========================
-- CLIENTES
-- =========================
INSERT INTO cliente (id, nombre, email, telefono, fecha_registro) VALUES
  (1, 'Juan Perez', 'juan.perez@demo.com', '600123456', '2025-01-10 09:10:00'),
  (2, 'Maria Lopez', 'maria.lopez@demo.com', '600987654', '2025-01-12 10:45:00'),
  (3, 'Carlos Ruiz', 'carlos.ruiz@demo.com', '611223344', '2025-01-15 16:20:00'),
  (4, 'Laura Garcia', 'laura.garcia@demo.com', '622334455', '2025-01-18 11:30:00'),
  (5, 'Andres Martinez', 'andres.martinez@demo.com', '633445566', '2025-01-20 13:40:00'),
  (6, 'Sofia Torres', 'sofia.torres@demo.com', '644556677', '2025-01-25 18:15:00'),
  (7, 'Diego Herrera', 'diego.herrera@demo.com', '655667788', '2025-02-01 08:25:00'),
  (8, 'Ana Navarro', 'ana.navarro@demo.com', '666778899', '2025-02-04 12:50:00'),
  (9, 'Javier Santos', 'javier.santos@demo.com', '677889900', '2025-02-11 14:00:00'),
  (10, 'Lucia Molina', 'lucia.molina@demo.com', '688990011', '2025-02-15 17:35:00');

-- =========================
-- VEHICULOS
-- =========================
INSERT INTO vehiculo (id, marca, modelo, matricula, precio_por_dia, disponible, categoria_id) VALUES
  (1, 'Toyota', 'Yaris', '1234-ABC', 34.90, TRUE, 1),
  (2, 'Kia', 'Rio', '2345-BCD', 36.50, TRUE, 1),
  (3, 'Seat', 'Leon', '3456-CDE', 42.00, TRUE, 2),
  (4, 'Volkswagen', 'Golf', '4567-DEF', 48.75, TRUE, 2),
  (5, 'Nissan', 'Qashqai', '5678-EFG', 62.00, FALSE, 3),
  (6, 'Hyundai', 'Tucson', '6789-FGH', 66.90, TRUE, 3),
  (7, 'Tesla', 'Model 3', '7890-GHI', 89.00, FALSE, 4),
  (8, 'Renault', 'Megane E-Tech', '8901-HIJ', 74.50, TRUE, 4),
  (9, 'Skoda', 'Octavia Combi', '9012-IJK', 58.40, TRUE, 5),
  (10, 'Peugeot', '5008', '0123-JKL', 71.30, TRUE, 5),
  (11, 'BMW', 'Serie 3', '1122-KLM', 115.00, FALSE, 6),
  (12, 'Mercedes-Benz', 'Clase C', '2233-LMN', 122.50, TRUE, 6),
  (13, 'Dacia', 'Sandero', '3344-MNO', 31.20, TRUE, 1),
  (14, 'Ford', 'Focus', '4455-NOP', 44.80, TRUE, 2),
  (15, 'Audi', 'Q5', '5566-OPQ', 119.90, TRUE, 6);

-- =========================
-- ALQUILERES
-- =========================
INSERT INTO alquiler (id, fecha_inicio, fecha_fin, precio_total, cliente_id, vehiculo_id) VALUES
  (1, '2025-02-03', '2025-02-06', 104.70, 1, 1),
  (2, '2025-02-05', '2025-02-10', 210.00, 2, 3),
  (3, '2025-02-07', '2025-02-09', 124.00, 3, 5),
  (4, '2025-02-08', '2025-02-12', 356.00, 4, 7),
  (5, '2025-02-10', '2025-02-14', 293.60, 1, 9),
  (6, '2025-02-11', '2025-02-13', 244.00, 5, 12),
  (7, '2025-02-13', '2025-02-18', 333.50, 6, 10),
  (8, '2025-02-14', '2025-02-16', 133.80, 7, 6),
  (9, '2025-02-16', '2025-02-20', 359.60, 8, 15),
  (10, '2025-02-18', '2025-02-21', 146.40, 9, 14),
  (11, '2025-02-19', '2025-02-23', 498.00, 10, 11),
  (12, '2025-02-21', '2025-02-24', 223.50, 2, 8),
  (13, '2025-02-22', '2025-02-26', 249.60, 3, 6),
  (14, '2025-02-23', '2025-02-25', 84.00, 4, 2),
  (15, '2025-02-24', '2025-02-28', 490.00, 5, 12),
  (16, '2025-02-25', '2025-03-01', 236.00, 6, 9),
  (17, '2025-02-27', '2025-03-03', 357.20, 1, 15),
  (18, '2025-02-28', '2025-03-04', 167.00, 7, 4),
  (19, '2025-03-01', '2025-03-05', 356.00, 8, 7),
  (20, '2025-03-02', '2025-03-07', 223.20, 9, 14);

COMMIT;
