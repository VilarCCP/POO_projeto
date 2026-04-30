CREATE DATABASE IF NOT EXISTS sistema_rh;
USE sistema_rh;

CREATE TABLE IF NOT EXISTS funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('CLT', 'PJ', 'HORISTA') NOT NULL,
    nome VARCHAR(100) NOT NULL,
    depto VARCHAR(50) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    salario_base DECIMAL(10,2),
    valor_hora DECIMAL(10,2),
    horas_trabalhadas INT,
    valor_contrato DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS eventos_folha (
    id INT AUTO_INCREMENT PRIMARY KEY,
    funcionario_id INT NOT NULL,
    tipo_evento ENUM('ADICIONAL', 'DESCONTO') NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE CASCADE
);
