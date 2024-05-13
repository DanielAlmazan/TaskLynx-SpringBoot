DROP TABLE IF EXISTS trabajo;
DROP TABLE IF EXISTS trabajador;

CREATE TABLE IF NOT EXISTS trabajador
(
    id_trabajador VARCHAR(5) PRIMARY KEY,
    dni           VARCHAR(9) UNIQUE   NOT NULL,
    nombre        VARCHAR(100)        NOT NULL,
    apellidos     VARCHAR(100)        NOT NULL,
    especialidad  VARCHAR(50)         NOT NULL,
    contraseña    VARCHAR(50)         NOT NULL,
    email         VARCHAR(150) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS trabajo
(
    cod_trabajo   VARCHAR(5) PRIMARY KEY,
    categoria     VARCHAR(50)   NOT NULL,
    descripcion   VARCHAR(500)  NOT NULL,
    fec_ini       DATE          NOT NULL,
    fec_fin       DATE,
    tiempo        NUMERIC(4, 1),
    id_trabajador VARCHAR(5),
    prioridad     NUMERIC(1, 0) NOT NULL,
    CONSTRAINT fk_trabajador FOREIGN KEY (id_trabajador) REFERENCES trabajador (id_trabajador)
);

INSERT INTO trabajador (id_trabajador, dni, nombre, apellidos, especialidad, "contraseña", email)
VALUES ('T001', '12345678A', 'Juan', 'Pérez', 'Carpintería', 'contraseña1', 'juan.perez@example.com'),
       ('T002', '23456789B', 'Ana', 'Gómez', 'Fontanería', 'contraseña2', 'ana.gomez@example.com'),
       ('T003', '34567890C', 'Carlos', 'Martínez', 'Electricidad', 'contraseña3', 'carlos.martinez@example.com');

INSERT INTO trabajo (cod_trabajo, categoria, descripcion, fec_ini, fec_fin, tiempo, id_trabajador, prioridad)
VALUES ('J001', 'Carpintería', 'Construcción de armario', '2022-01-01', NULL, 10.0, 'T001', 1),
       ('J002', 'Fontanería', 'Reparación de tuberías', '2022-01-02', NULL, 5.0, 'T002', 2),
       ('J003', 'Electricidad', 'Instalación de luces', '2022-01-03', NULL, 8.0, 'T003', 3);


CREATE EXTENSION IF NOT EXISTS unaccent;
