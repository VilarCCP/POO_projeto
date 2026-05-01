CONFIG XAMPP

CREATE DATABASE IF NOT EXISTS sistema_rh;
USE sistema_rh;

CREATE TABLE funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    tipo VARCHAR(10) NOT NULL, -- CLT, HORISTA, PJ
    nome VARCHAR(100) NOT NULL,
    depto VARCHAR(100),
    ativo BOOLEAN NOT NULL,

    salario_base DECIMAL(10,2),
    valor_hora DECIMAL(10,2),
    horas_trabalhadas INT,
    valor_contrato DECIMAL(10,2)
);
