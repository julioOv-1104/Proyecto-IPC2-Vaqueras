
CREATE DATABASE game_eshop;
USE game_eshop;

CREATE TABLE usuario (
    correo_usuario VARCHAR(255) PRIMARY KEY,
    contrasenna VARCHAR(255),
    nickname VARCHAR(255) UNIQUE,
    fecha_nacimiento DATE,
    telefono VARCHAR(50),
    pais VARCHAR(255)
);

CREATE TABLE usuario_comun (
    correo_usuario VARCHAR(255),
    biblioteca_visible BOOLEAN DEFAULT TRUE,
    cartera DOUBLE DEFAULT 0,
    PRIMARY KEY (correo_usuario),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo_usuario)
);

CREATE TABLE admin (
    correo_usuario VARCHAR(255),
    PRIMARY KEY (correo_usuario),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo_usuario)
);

CREATE TABLE empresa (
    nombre_empresa VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(255),
    comision DOUBLE
);

INSERT INTO empresa (nombre_empresa, descripcion, comision) 
VALUES ('activision', 'no mas CODs anuales', 10), ('ubisoft','tenemos muchos buggs',10);

CREATE TABLE empresario (
    correo_usuario VARCHAR(255),
    nombre VARCHAR(255),
    nombre_empresa VARCHAR(255),
    PRIMARY KEY (correo_usuario),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo_usuario),
    FOREIGN KEY (nombre_empresa) REFERENCES empresa(nombre_empresa)
);

CREATE TABLE juego (
    titulo VARCHAR(255) PRIMARY KEY,
    descripcion VARCHAR(255),
    precio DOUBLE,
    clasificacion ENUM('E','T','M'), 
    fecha_lanzamiento DATE,
    multimedia MEDIUMBLOB, 
    tipo_multimedia VARCHAR(50),
    habilitado BOOLEAN DEFAULT TRUE,
    nombre_empresa VARCHAR(255),
    FOREIGN KEY (nombre_empresa) REFERENCES empresa(nombre_empresa)
);

INSERT INTO juego (titulo, descripcion, precio, clasificacion, fecha_lanzamiento, nombre_empresa) 
VALUES ('Assassin´s Creed', 'Mundo abierto en tercera persona', 59.99, 'T', '2024-10-29', 'ubisoft'),
('Call of Duty', 'Juego de disparos en primera persona', 69.99, 'M', '2025-10-29', 'activision');

CREATE TABLE compra (
    correo_usuario VARCHAR(255),
    titulo VARCHAR(255),
    fecha_compra DATE,
    monto DOUBLE,
    porcentaje DOUBLE,
    estado BOOLEAN DEFAULT false,
    PRIMARY KEY (correo_usuario, titulo),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo_usuario),
    FOREIGN KEY (titulo) REFERENCES juego(titulo)
);

CREATE TABLE instalacion_prestamo (
    correo_usuario VARCHAR(255),
    titulo VARCHAR(255),
    fecha_instalacion DATE,
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo_usuario),
    FOREIGN KEY (titulo) REFERENCES juego(titulo)
);

CREATE TABLE comentario (
    correo_usuario VARCHAR(255),
    titulo VARCHAR(255),
    id_comentario INT AUTO_INCREMENT,
    texto_comentario VARCHAR(255),
    id_comentario_padre INT,
    visible BOOLEAN DEFAULT true,
    PRIMARY KEY (id_comentario),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo_usuario),
    FOREIGN KEY (titulo) REFERENCES juego(titulo),
    FOREIGN KEY (id_comentario_padre) REFERENCES comentario(id_comentario)
);

CREATE TABLE calificacion (
    correo_usuario VARCHAR(255),
    titulo VARCHAR(255),
    estrellas DOUBLE,
    PRIMARY KEY (correo_usuario, titulo),
    FOREIGN KEY (correo_usuario) REFERENCES usuario(correo_usuario),
    FOREIGN KEY (titulo) REFERENCES juego(titulo)
);

CREATE TABLE grupo_familiar (
    id_grupo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    correo_encargado VARCHAR(255),
    FOREIGN KEY (correo_encargado) REFERENCES usuario(correo_usuario)
);

CREATE TABLE miembro_grupo (
    id_grupo INT,
    correo_miembro VARCHAR(255),
    PRIMARY KEY (id_grupo, correo_miembro),
    FOREIGN KEY (id_grupo) REFERENCES grupo_familiar(id_grupo),
    FOREIGN KEY (correo_miembro) REFERENCES usuario(correo_usuario)
);

CREATE TABLE comision (
    porcentaje DOUBLE
);

INSERT INTO comision (porcentaje) VALUES (15);

CREATE TABLE categoria (
    nombre_categoria VARCHAR(255) PRIMARY KEY
);

INSERT INTO categoria (nombre_categoria) VALUES ('accion'), ('aventura'), ('RPG'), ('estrategia'), 
('simulacion'), ('deportes'), ('puzzles'), ('indie');

CREATE TABLE juego_categoria (
    titulo VARCHAR(255),
    nombre_categoria VARCHAR(255),
     PRIMARY KEY (titulo, nombre_categoria),
    FOREIGN KEY (titulo) REFERENCES juego(titulo),
    FOREIGN KEY (nombre_categoria) REFERENCES categoria(nombre_categoria)
);

INSERT INTO juego_categoria (titulo, nombre_categoria) 
VALUES ('Call of Duty', 'accion'), ('Assassin´s Creed','accion'),('Assassin´s Creed', 'aventura');

CREATE TABLE recurso_minimo (
    nombre_recurso VARCHAR(255) PRIMARY KEY
);

INSERT INTO recurso_minimo (nombre_recurso) 
VALUES ('sistema operativo'), ('almacenamiento'), ('memoria RAM'), ('procesador');

CREATE TABLE juego_recurso (
    titulo VARCHAR(255),
    nombre_recurso VARCHAR(255),
    valor VARCHAR(255),
    FOREIGN KEY (titulo) REFERENCES juego(titulo),
    FOREIGN KEY (nombre_recurso) REFERENCES recurso_minimo(nombre_recurso)
);

INSERT INTO juego_recurso (titulo, nombre_recurso, valor) VALUES ('Assassin´s Creed','almacenamiento','80GB'), 
('Assassin´s Creed','memoria RAM','8GB'), ('Assassin´s Creed','procesador','Intel Core i5-2500K'), 
('Assassin´s Creed','sistema operativo','Windows 10, Linux'), ('Call of Duty','almacenamiento','200GB'), 
('Call of Duty','memoria RAM','16GB'), ('Call of Duty','procesador','Intel Core i7-3770'), 
('Call of Duty','sistema operativo','Windows 10, Linux');