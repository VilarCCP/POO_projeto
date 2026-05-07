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






# Sistema de RH — Folha Simplificada

Projeto desenvolvido para a disciplina de Programação Orientada a Objetos utilizando Java, JDBC e MySQL/MariaDB.

---

#  Objetivo do Projeto

O sistema simula um ambiente simples de RH, permitindo:

- Cadastro de funcionários
- Gerenciamento de eventos da folha
- Cálculo de pagamentos
- Relatórios gerenciais
- Persistência em banco de dados

O projeto aplica conceitos de:

- Programação Orientada a Objetos
- Herança
- Polimorfismo
- Encapsulamento
- JDBC
- CRUD
- Arquitetura em camadas

---

#  Tecnologias Utilizadas

- Java
- Maven
- JDBC
- MySQL / MariaDB
- MySQL Workbench
- Console (CLI)

---

#  Estrutura do Projeto

```text
src/
└── main/
    └── java/
        └── rh/
            ├── dao/
            │   ├── ConnectionFactory.java
            │   ├── EventoFolhaDAO.java
            │   └── FuncionarioDAO.java
            │
            ├── modelo/
            │   ├── EventoFolha.java
            │   ├── Funcionario.java
            │   ├── FuncionarioCLT.java
            │   ├── FuncionarioPJ.java
            │   └── FuncionarioHorista.java
            │
            └── Main.java
```

---

#  Configuração do Banco de Dados

O projeto acompanha um arquivo SQL:

```text
sistema_rh.sql
```

Esse arquivo contém:

- criação do banco
- criação das tabelas
- relacionamentos
- chaves primárias e estrangeiras

---

#  Como Executar o Banco

## Opção 1 — MySQL Workbench

### 1. Abrir o MySQL Workbench

Conectar ao servidor local.

Exemplo:

```text
Local instance MySQL80
```

---

### 2. Abrir o script SQL

No menu:

```text
File → Open SQL Script
```

Selecionar:

```text
sistema_rh.sql
```

---

### 3. Executar o script

Clicar no ícone do raio 

ou utilizar:

```text
Ctrl + Shift + Enter
```

---

### 4. Atualizar os schemas

Na aba lateral:

```text
SCHEMAS
```

Clique com botão direito:

```text
Refresh All
```

---

## Resultado Esperado

O banco:

```text
sistema_rh
```

deve conter as tabelas:

```text
funcionarios
eventos_folha
```

---

# Configuração da Conexão

No arquivo:

```text
ConnectionFactory.java
```

configure:

```java
private static final String URL =
"jdbc:mariadb://localhost:3306/sistema_rh?useTimezone=true&serverTimezone=UTC";

private static final String USER = "root";
private static final String PASS = "SUA_SENHA";
```

> Caso utilize XAMPP/MariaDB, normalmente o usuário é `root` e a senha é vazia.

---

#  Executando o Projeto

## Pelo NetBeans / Eclipse / VS Code

1. Abrir o projeto Maven
2. Aguardar o download das dependências
3. Executar:

```text
Main.java
```

---

#  Funcionalidades do Sistema

O menu possui as seguintes opções:

```text
1. Cadastrar Funcionário
2. Listar Funcionários
3. Atualizar Funcionário
4. Deletar Funcionário
5. Adicionar Evento de Folha
6. Gerar Relatório: Folha do Mês
7. Gerar Relatório: Totais por Tipo
8. Sair
```

---

#  Tipos de Funcionário

O sistema trabalha com herança através da classe abstrata:

```text
Funcionario
```

Especializações:

- FuncionarioCLT
- FuncionarioPJ
- FuncionarioHorista

Cada tipo implementa seu próprio método:

```java
calcularPagamento()
```

---

#  Eventos da Folha

Os eventos podem ser:

- ADICIONAL
- DESCONTO

Regras:

- adicionais somam ao pagamento
- descontos subtraem
- funcionários inativos não entram na folha

---

#  Conceitos de POO Aplicados

## Encapsulamento

Todos os atributos são privados e acessados via getters/setters.

---

## Herança

A classe `Funcionario` é utilizada como superclasse das especializações.

---

## Polimorfismo

Os funcionários são manipulados através da referência da superclasse:

```java
List<Funcionario>
```

permitindo execução dinâmica do método:

```java
calcularPagamento()
```

---

## Override

Cada subclasse sobrescreve o método:

```java
calcularPagamento()
```

---

#  Possíveis Problemas

## Erro de conexão

Verificar:

- banco ligado
- usuário/senha corretos
- porta 3306 ativa

---

## Erro:

```text
Public Key Retrieval is not allowed
```

Adicionar na URL JDBC:

```text
allowPublicKeyRetrieval=true
```

---

## Erro de versão Java

Verificar se:
- o projeto utiliza a mesma versão do JDK instalada

---

#  Teste Rápido

Fluxo recomendado:

1. Cadastrar funcionário
2. Listar funcionários
3. Adicionar evento
4. Gerar relatório

---

#  Observações Finais

O sistema foi desenvolvido seguindo arquitetura em camadas:

- model
- dao

Os dados são persistidos em banco MySQL/MariaDB utilizando JDBC.

O projeto pode ser expandido futuramente para:
- interface gráfica
- API REST
- autenticação
- sistema web

---

#  Projeto Acadêmico

Disciplina:
Programação Orientada a Objetos

Tema:
Sistema de RH — Folha Simplificada
