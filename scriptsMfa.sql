CREATE DATABASE IF NOT EXISTS seguridadMfa;
USE seguridadMfa;

CREATE TABLE codigo_mfa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);