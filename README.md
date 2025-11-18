## 🦁 Projeto Zoológico - Desenvolvimento Web (4º Semestre)

---

### 📄 Introdução

Bem-vindos ao projeto de **Gerenciamento de Zoológico**!

Este projeto é um exercício proposto para a disciplina de **Desenvolvimento Web** do 4º semestre do Centro Universitário Senac. Nosso objetivo é construir uma aplicação web robusta, utilizando o framework **Spring Boot** com o padrão de arquitetura **MVC (Model-View-Controller)**, para simular e gerenciar as operações de um zoológico.

A aplicação foi desenvolvida com foco em:
* **Modelagem de Dados Eficiente:** O banco de dados foi desenhado para representar as entidades cruciais de um zoológico, como animais, espécies, habitats, eventos, veterinários, tratadores e usuários. **A estrutura completa do nosso Schema (UML) é a base do nosso banco de dados.**
    

![Diagrama UML do Banco de Dados do Zoológico](image/uml-zoo.png)

* **Segurança:** Implementação de **JSON Web Tokens (JWT)** para autenticação e autorização seguras.
* **Boas Práticas de Código:** Utilização de **Data Transfer Objects (DTOs)** para estruturar e tipar a comunicação de dados entre as camadas da aplicação, garantindo maior clareza e manutenção.

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

    \`\`\`sql
    CREATE DATABASE zoo;
    \`\`\`
    *Obs.: A criação das tabelas e a inserção dos dados iniciais (se houver) serão feitas automaticamente pelo Spring Boot ao rodar a aplicação, graças às configurações no `application.yml` (e, possivelmente, aos arquivos `schema.sql` e `data.sql` no diretório de recursos).*

#### 2. Configuração do Projeto (`application.yml`)

O arquivo de configuração da aplicação está localizado em `src/main/resources/application.yml`. **É crucial que você atualize as credenciais do banco de dados para corresponder ao seu ambiente MySQL.**

Localize a seção de `spring.datasource` e edite os campos `username` e `password`:

\`\`\`yaml
spring:
  datasource:
    # URL de conexão deve ser mantida, a menos que você mude a porta padrão do MySQL
    url: jdbc:mysql://localhost:3306/zoo?createDatabaseIfNotExist=true&serverTimezone=UTC
    driver-class-name: com.mysql.cj.jdbc.Driver
    # ATUALIZE COM SUAS CREDENCIAIS DO MYSQL
    username: seu_username_aqui
    password: sua_password_aqui
  jpa:
    hibernate:
      ddl-auto: update # Configurado para atualizar o schema
    show-sql: true
    properties:
      hibernate:
        format_sql: true
\`\`\`

#### 3. Execução do Projeto

1.  Clone este repositório para sua máquina local.
    \`\`\`bash
    git clone https://aws.amazon.com/pt/what-is/repo/
    \`\`\`
2.  Abra o projeto em sua IDE (como IntelliJ IDEA ou VS Code).
3.  Garanta que a dependência **Maven** (ou Gradle) seja resolvida.
4.  Execute a classe principal da aplicação (aquela com a anotação `@SpringBootApplication`).

O servidor será iniciado na porta padrão (geralmente `8080`).

---

### 🎯 Contato e Suporte

Em caso de dúvidas sobre a estrutura do banco de dados, a implementação de DTOs, ou as rotas da API, consulte a documentação interna ou entre em contato com um dos membros do grupo.

| Membro | Função no Projeto | Contato (e-mail/GitHub) |
| :--- | :--- | :--- |
| [Seu Nome] | Líder/Back-end | [seu-email] |
| [Nome do Colega 2] | Back-end/Database | [email do colega] |
| [Nome do Colega 3] | Front-end (se houver) | [email do colega] |

---
