
-- Script de creación de base de datos AM SOUND
-- Hugo Vélez Fernández
-- V 1.0 - 07/03/2024

-- DROP DATABASE IF EXISTS AMSOUND;
-- CREATE DATABASE AMSOUND;
-- USE AMSOUND;

DROP TABLE COMPONENTE_VOZ;
DROP TABLE RECURSO;
DROP TABLE VOZ;
DROP TABLE PIEZA_MUSICAL;
DROP TABLE AGRUPACION;
DROP TABLE USUARIO;

DROP SEQUENCE SEQ_USUARIO;
DROP SEQUENCE SEQ_PIEZA_MUSICAL;
DROP SEQUENCE SEQ_AGRUPACION;
DROP SEQUENCE SEQ_VOZ;
DROP SEQUENCE SEQ_COMPONENTE_VOZ;
DROP SEQUENCE SEQ_RECURSO;

CREATE TABLE USUARIO (
    ID NUMBER(6,0) PRIMARY KEY,
    NOMBRE VARCHAR2(64) NOT NULL,
    MAIL VARCHAR2(255) NOT NULL UNIQUE,
    TELEFONO CHAR(9) UNIQUE,
    CONTRASENA CHAR(64) NOT NULL,
    FOTO VARCHAR2(255),

    CONSTRAINT "ValidarMail" CHECK (MAIL LIKE '%@%.%')
);

CREATE TABLE AGRUPACION (
    ID NUMBER(6,0) PRIMARY KEY,
    NOMBRE VARCHAR2(64) NOT NULL UNIQUE,
    CONTACTO VARCHAR2(255) NOT NULL,
    TEXTO VARCHAR2(255),
    FOTO VARCHAR2(255),
    DIRECTOR_DE_AGRUPACION NUMBER(6) NOT NULL,

    FOREIGN KEY (DIRECTOR_DE_AGRUPACION) REFERENCES USUARIO(ID)
);

CREATE TABLE PIEZA_MUSICAL (
    ID NUMBER(6,0) PRIMARY KEY,
    NOMBRE VARCHAR2(64) NOT NULL,
    AUTOR VARCHAR2(64) NOT NULL,
    TEXTO VARCHAR2(255),
    PIEZA_DE_AGRUPACION NUMBER(6) NOT NULL,
    
    FOREIGN KEY (PIEZA_DE_AGRUPACION) REFERENCES AGRUPACION(ID)
);

CREATE TABLE VOZ (
    ID NUMBER(6,0) PRIMARY KEY,
    NOMBRE VARCHAR2(64) NOT NULL,
    TEXTO VARCHAR2(255),
    VOZ_DE_AGRUPACION NUMBER(6) NOT NULL,

    FOREIGN KEY (VOZ_DE_AGRUPACION) REFERENCES AGRUPACION(ID)
);

CREATE TABLE RECURSO (
    ID NUMBER(8,0) PRIMARY KEY,
    TITULO VARCHAR2(64) NOT NULL,
    RUTA_ARCHIVO VARCHAR2(255) NOT NULL,
    TEXTO VARCHAR2(255),
    VOZ_DE_RECURSO NUMBER(6) NOT NULL,
    PIEZA_DE_RECURSO NUMBER(6) NOT NULL,

    FOREIGN KEY (VOZ_DE_RECURSO) REFERENCES VOZ(ID),
    FOREIGN KEY (PIEZA_DE_RECURSO) REFERENCES PIEZA_MUSICAL(ID)
);

CREATE TABLE COMPONENTE_VOZ (
    ID NUMBER(6,0) PRIMARY KEY,
    USUARIO_COMPONENTE NUMBER(6) NOT NULL,
    VOZ NUMBER(6) NOT NULL,

    FOREIGN KEY (USUARIO_COMPONENTE) REFERENCES USUARIO(ID),
    FOREIGN KEY (VOZ) REFERENCES VOZ(ID),
    
    -- Restricción de unicidad compuesta
    CONSTRAINT unique_usuario_voz UNIQUE (USUARIO_COMPONENTE, VOZ)
);

CREATE SEQUENCE SEQ_USUARIO INCREMENT BY 1 START WITH 10 MAXVALUE 999999 CYCLE;
CREATE SEQUENCE SEQ_PIEZA_MUSICAL INCREMENT BY 1 START WITH 10 MAXVALUE 999999 CYCLE;
CREATE SEQUENCE SEQ_AGRUPACION INCREMENT BY 1 START WITH 10 MAXVALUE 999999 CYCLE;
CREATE SEQUENCE SEQ_VOZ INCREMENT BY 1 START WITH 10 MAXVALUE 999999 CYCLE;
CREATE SEQUENCE SEQ_COMPONENTE_VOZ INCREMENT BY 1 START WITH 10 MAXVALUE 999999 CYCLE;
CREATE SEQUENCE SEQ_RECURSO INCREMENT BY 1 START WITH 10 MAXVALUE 99999999 CYCLE;

-- Datos para la tabla USUARIO
INSERT INTO USUARIO (ID, NOMBRE, MAIL, TELEFONO, CONTRASENA, FOTO) VALUES
(1, 'Juan Perez', 'juan.perez@example.com', '123456789', 'kk', null),
(2, 'Maria Gomez', 'maria.gomez@example.com', '234567890', 'kk', null),
(3, 'Carlos Lopez', 'carlos.lopez@example.com', '345678901', 'kk', null),
(4, 'Ana Fernandez', 'ana.fernandez@example.com', '456789012', 'kk', null),
(5, 'Luis Martinez', 'luis.martinez@example.com', '567890123', 'kk', null);

-- Datos para la tabla AGRUPACION
INSERT INTO AGRUPACION (ID, NOMBRE, CONTACTO, TEXTO, FOTO, DIRECTOR_DE_AGRUPACION) VALUES
(1, 'Coro Universitario', 'contacto@coro-universitario.com', 'Coro de la universidad', null, 1),
(2, 'Banda Sinfónica', 'contacto@banda-sinfonica.com', 'Banda sinfónica de la ciudad', null, 2),
(3, 'Orquesta Filarmónica', 'contacto@orquesta-filarmonica.com', 'Orquesta filarmónica nacional', null, 3);

-- Datos para la tabla PIEZA_MUSICAL
INSERT INTO PIEZA_MUSICAL (ID, NOMBRE, AUTOR, TEXTO, PIEZA_DE_AGRUPACION) VALUES
(1, 'Sinfonía No. 5', 'Ludwig van Beethoven', 'Sinfonía en do menor', 3),
(2, 'Canon en Re', 'Johann Pachelbel', 'Canon clásico', 2),
(3, 'Oda a la Alegría', 'Friedrich Schiller', 'Himno a la alegría', 1);

-- Datos para la tabla VOZ
INSERT INTO VOZ (ID, NOMBRE, TEXTO, VOZ_DE_AGRUPACION) VALUES
(1, 'Soprano', 'Voz aguda femenina', 1),
(2, 'Alto', 'Voz media femenina', 1),
(3, 'Tenor', 'Voz aguda masculina', 1),
(4, 'Bajo', 'Voz grave masculina', 1),
(5, 'Soprano', 'Voz aguda femenina', 2),
(6, 'Alto', 'Voz media femenina', 2),
(7, 'Tenor', 'Voz aguda masculina', 2),
(8, 'Bajo', 'Voz grave masculina', 2),
(9, 'Soprano', 'Voz aguda femenina', 3),
(10, 'Alto', 'Voz media femenina', 3),
(11, 'Tenor', 'Voz aguda masculina', 3),
(12, 'Bajo', 'Voz grave masculina', 3);

-- Datos para la tabla RECURSO
INSERT INTO RECURSO (ID, TITULO, RUTA_ARCHIVO, TEXTO, VOZ_DE_RECURSO, PIEZA_DE_RECURSO) VALUES
(1, 'Recurso 1', '/recursos/recurso1.mp3', 'Recurso de prueba 1', 1, 1),
(2, 'Recurso 2', '/recursos/recurso2.mp3', 'Recurso de prueba 2', 2, 2),
(3, 'Recurso 3', '/recursos/recurso3.mp3', 'Recurso de prueba 3', 3, 3),
(4, 'Recurso 4', '/recursos/recurso4.mp3', 'Recurso de prueba 4', 4, 1),
(5, 'Recurso 5', '/recursos/recurso5.mp3', 'Recurso de prueba 5', 5, 2),
(6, 'Recurso 6', '/recursos/recurso6.mp3', 'Recurso de prueba 6', 6, 3);

-- Datos para la tabla COMPONENTE_VOZ
INSERT INTO COMPONENTE_VOZ (ID, USUARIO_COMPONENTE, VOZ) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 1, 5),
(6, 2, 6),
(7, 3, 7),
(8, 4, 8),
(9, 5, 9),
(10, 1, 10),
(11, 2, 11),
(12, 3, 12);
