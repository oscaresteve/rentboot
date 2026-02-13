-- Roles
INSERT INTO rol (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO rol (nombre) VALUES ('ROLE_USER');

-- Usuario admin (password se cambiará luego por uno encriptado)
INSERT INTO usuario (username, password, enabled)
VALUES ('admin', 'admin123', true);

-- Categorías
INSERT INTO categoria (nombre, descripcion)
VALUES ('SUV', 'Vehículos deportivos utilitarios'),
       ('Compacto', 'Vehículos pequeños'),
       ('Electrico', 'Vehículos eléctricos');

-- Cliente ejemplo
INSERT INTO cliente (nombre, email, telefono)
VALUES ('Juan Pérez', 'juan@email.com', '600123456');
