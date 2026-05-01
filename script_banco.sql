-- Criação do Banco de Dados
CREATE DATABASE IF NOT EXISTS sistema_rh;
USE sistema_rh;

-- Criação da Tabela Principal
CREATE TABLE funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(10) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    depto VARCHAR(100),
    ativo BOOLEAN NOT NULL,
    salario_base DECIMAL(10,2),
    valor_hora DECIMAL(10,2),
    horas_trabalhadas INT,
    valor_contrato DECIMAL(10,2)
);

-- Criação da Tabela de Eventos (Adicionais e Descontos)
CREATE TABLE eventos_folha (
    id INT AUTO_INCREMENT PRIMARY KEY,
    funcionario_id INT NOT NULL,
    tipo_evento VARCHAR(50) NOT NULL,
    descricao VARCHAR(255),
    valor DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE CASCADE
);