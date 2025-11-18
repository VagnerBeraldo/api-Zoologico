## 🦁 Projeto Zoológico - Desenvolvimento Web (4º Semestre)

---

### 📄 Introdução

Bem-vindos ao projeto de **Gerenciamento de Zoológico**!

Este projeto é um exercício proposto para a disciplina de **Desenvolvimento Web** do 4º semestre do Centro Universitário Senac. Nosso objetivo é construir uma aplicação web robusta, utilizando o framework **Spring Boot** com o padrão de arquitetura **MVC (Model-View-Controller)**, para simular e gerenciar as operações de um zoológico.

A aplicação foi desenvolvida com foco em:
* **Modelagem de Dados Eficiente:** O banco de dados foi desenhado para representar as entidades cruciais de um zoológico, como animais, espécies, habitats, eventos, veterinários, tratadores e usuários. **A estrutura completa do nosso Schema (UML) é a base do nosso banco de dados.**
    
<br><br>
![Diagrama UML do Zoológico](./uml-zoo.png)
<br><br>

* **Segurança:** Implementação de **JSON Web Tokens (JWT)** para autenticação e autorização seguras.
* **Boas Práticas de Código:** Utilização de **Data Transfer Objects (DTOs)**, **Mappers** para estruturar e tipar a comunicação de dados entre as camadas da aplicação, garantindo maior clareza e manutenção.

---

### ⚙️ Tecnologias Principais

| Tecnologia | Versão | Função |
| :--- | :--- | :--- |
| **Spring Boot** | **3.5.7** | Framework principal para a construção da API e da aplicação web. |
| **Java** | **21** | Linguagem de programação. |
| **Padrão** | **MVC Web** | Arquitetura do projeto. |
| **Banco de Dados** | **MySQL** | Sistema de Gerenciamento de Banco de Dados. |
| **Segurança** | **JWT** | Geração e validação de tokens de acesso. |
| **Design** | **DTO** | Padrão para transferência de dados. |

---

### 🛠️ Configuração do Ambiente e Banco de Dados

Para rodar o projeto, é necessário ter o **Java 21** e um servidor **MySQL** instalados.

#### 1. Configuração do MySQL

O projeto foi configurado para utilizar o **MySQL**. Siga as instruções abaixo para preparar o banco de dados:

* **Criação do Schema:**
    É obrigatório criar um banco de dados com o nome exato **`zoo`**. Você pode fazer isso executando o seguinte comando SQL em seu cliente MySQL (como MySQL Workbench, DBeaver, ou linha de comando):

    * CREATE DATABASE zoo;
    *Obs.: A criação das tabelas serão feitas automaticamente pelo Spring Boot ao rodar a aplicação*

#### 2. Configuração do Projeto (`application.yml`)

O arquivo de configuração da aplicação está localizado em `src/main/resources/application.yml`. **É crucial que você atualize as credenciais do banco de dados para corresponder ao seu ambiente MySQL.**

Edite os campos `username` e `password` para seus dados pessoais:

#### 3. Execução do Projeto

1.  Clone este repositório para sua máquina local.
    
    git clone https://github.com/xxxxxx/
    
2.  Abra o projeto em sua IDE (como IntelliJ IDEA ou VS Code).
3.  Garanta que a dependência **Maven** seja resolvida.
4.  Execute a classe principal da aplicação (aquela com a anotação `@SpringBootApplication`).

O servidor será iniciado na porta padrão (`8080`).

---

### 🎯 Grupo

| Membro | Função no Projeto | Contato (GitHub) |
| :--- | :--- | :--- |
| [Erick Guimarães] | Líder/Back-end | [Erick](https://github.com/ErickGX) |
| [Vagner Beraldo] | Código Seco | [Vagner](https://github.com/VagnerBeraldo) |
| [Daiane Vitória] | Em análise | [Daiane](https://github.com/Vitoriaraso) |

---
