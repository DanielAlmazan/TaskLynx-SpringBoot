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
VALUES ('T001', '12345678A', 'Juan', 'Pérez', 'Carpintería', 'c1', 'juan.perez@example.com'),
       ('T002', '23456789B', 'Anaclet', 'Agent Secret', 'Electricidad', 'c2', 'anaclet.secret@example.com'),
       ('T003', '23456789A', 'Margaret', 'Agent Secret', 'Electricidad', 'c3', 'margaret.agent@example.com'),
       ('T004', '23456789M', 'Ana', 'Gómez', 'Fontanería', 'c4', 'ana.gomez@example.com'),
       ('T005', '34567890C', 'Carlos', 'Martínez', 'Pintura', 'c5', 'carlos.martinez@example.com'),
       ('TDUDE', '34567890T', 'Dude', 'The', 'None', 'Donnie', 'white.russian@example.com');

INSERT INTO trabajo (cod_trabajo, categoria, descripcion, fec_ini, fec_fin, tiempo, id_trabajador, prioridad)
VALUES ('J001', 'Carpintería', 'Construcción de armario', '2024-05-01', NULL, NULL, 'T001', 1),
       ('J002', 'Electricidad', 'Instalación de luces', '2024-05-02', NULL, NULL, 'T002', 2),
       ('J003', 'Electricidad', 'Desinstalación de luces', '2024-05-02', NULL, NULL, 'T003', 2),
       ('J004', 'Fontanería', 'Desatascar tuberías', '2024-05-02', NULL, NULL, 'T004', 2),
       ('J005', 'Pintura', 'Pintar la mona', '2024-05-03', NULL, NULL, 'T005', 3),
       ('J006', 'Electricidad', 'Enchufar enchufes', '2024-05-02', NULL, NULL, 'T002', 1),
       ('J007', 'Desarrollo', 'Desarrollar sueños', '2025-05-02', NULL, NULL, NULL, 4);


CREATE EXTENSION IF NOT EXISTS unaccent;
