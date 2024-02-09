create database GestorCasas;
use GestorCasas;

-- Otros campos de la casa
-- Crear tabla Casa
-- Crear tabla Casa
CREATE TABLE Casa (
    idCasa INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(100)
);	

-- Crear tabla Usuario
CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    edad INT
);

-- Crear tabla de relación UsuarioCasa
CREATE TABLE UsuarioCasa (
    idUsuario INT,
    idCasa INT,
    PRIMARY KEY (idUsuario, idCasa),
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario),
    FOREIGN KEY (idCasa) REFERENCES Casa(idCasa)
);

CREATE TABLE Comida (
    idComida INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    Fcaducidad date,
    idUser int,
    FOREIGN KEY (idUser) REFERENCES Usuario(idUsuario)
);

