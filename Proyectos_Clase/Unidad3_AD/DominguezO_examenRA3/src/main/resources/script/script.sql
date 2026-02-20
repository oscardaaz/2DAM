
DROP DATABASE IF EXISTS biblioteca_db;

CREATE DATABASE biblioteca_db CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;

USE biblioteca_db;

-- -------------------------------------------------------------
-- Tabla: usuarios
-- -------------------------------------------------------------
CREATE TABLE usuarios (
 nombre VARCHAR(50) NOT NULL,
 apellido VARCHAR(50) NOT NULL,
 email VARCHAR(100) UNIQUE NOT NULL,
 idUsuario INTEGER NOT NULL AUTO_INCREMENT,
 PRIMARY KEY (idUsuario)
);

-- -------------------------------------------------------------
-- Tabla: libros
-- -------------------------------------------------------------
CREATE TABLE libros (
 titulo VARCHAR(100) NOT NULL,
 autor VARCHAR(50) NOT NULL,
 anioPublicacion INTEGER NOT NULL,
 isbn VARCHAR(20) NOT NULL,
 PRIMARY KEY (isbn)
);


-- -------------------------------------------------------------
-- Tabla: prestamos
-- -------------------------------------------------------------
CREATE TABLE prestamos (
 idUsuario INTEGER NOT NULL,
 isbn VARCHAR(20) NOT NULL,
 fechaPrestamo DATE NOT NULL,
 fechaDevolucion DATE,
 idPrestamo INTEGER NOT NULL AUTO_INCREMENT,
 PRIMARY KEY (idPrestamo),
 FOREIGN KEY (idUsuario) REFERENCES usuarios(idUsuario),
 FOREIGN KEY (isbn) REFERENCES libros(isbn)
);
