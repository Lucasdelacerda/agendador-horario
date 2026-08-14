# 🚀 Agendador Horário API

> Sistema corporativo de gerenciamento e agendamento de horários, desenvolvido com foco em robustez, organização de regras de negócio e integridade dos dados.

---

## 📋 Sobre o Projeto

O **Agendador Horário** é uma API REST backend desenvolvida para atender às necessidades de prestadores de serviços, clínicas, salões e profissionais autônomos.

O principal diferencial do sistema é a **validação automática de conflitos de agenda**, impedindo a criação de agendamentos com horários sobrepostos e garantindo maior integridade dos dados.

O projeto também foi estruturado seguindo princípios de **arquitetura em camadas**, separando responsabilidades entre controllers, services, repositories e entities.

---

## 🛠️ Tecnologias e Ferramentas

| Tecnologia          | Utilização                            |
| ------------------- | ------------------------------------- |
| **Java 21**         | Linguagem principal                   |
| **Spring Boot 3.x** | Desenvolvimento da API REST           |
| **Spring Data JPA** | Persistência de dados                 |
| **Hibernate**       | ORM                                   |
| **MySQL**           | Banco de dados relacional             |
| **Docker**          | Containerização                       |
| **Docker Compose**  | Orquestração dos containers           |
| **Lombok**          | Redução de código boilerplate         |
| **Maven**           | Gerenciamento de dependências e build |

### 🔜 Tecnologias planejadas

* **Spring Security + JWT** — Autenticação e autorização
* **JUnit 5 + Mockito** — Testes automatizados
* **n8n** — Automação de processos e integração com Webhooks
* **WhatsApp / E-mail** — Notificações automáticas

---

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura em camadas para manter o código organizado, testável e de fácil manutenção.

```text
src
└── main
    ├── java
    │   └── ...
    │       ├── controller
    │       ├── service
    │       ├── repository
    │       └── entity
    │
    └── resources
        └── application.properties
```

### Responsabilidades

* **Controller** — Responsável pelos endpoints HTTP da API.
* **Service** — Contém as regras de negócio da aplicação.
* **Repository** — Responsável pela comunicação com o banco de dados.
* **Entity** — Representa as entidades persistidas no banco de dados.

---

## ⚙️ Regras de Negócio

### Prevenção de conflitos de agenda

Antes de criar um novo agendamento, a aplicação verifica se existe outro agendamento ocupando o mesmo intervalo de horário.

A validação considera:

* Data e hora de início do agendamento.
* Data e hora de finalização.
* Existência de outros agendamentos no mesmo período.

Caso seja identificado um conflito, a API impede a criação do novo registro.

Essa abordagem evita problemas como:

```text
09:00 ───────── 10:00
       Agendamento A

09:30 ───────── 10:30
       ❌ Conflito
```

Enquanto horários independentes são permitidos:

```text
09:00 ───────── 10:00
       Agendamento A

10:00 ───────── 11:00
       Agendamento B

       ✅ Sem conflito
```

---

## 🚀 Como Rodar o Projeto Localmente

### Pré-requisitos

Antes de executar o projeto, certifique-se de possuir:

* [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)
* Git

---

### 1. Clonar o repositório

```bash
git clone https://github.com/SEU-USUARIO/agendador-horario.git
cd agendador-horario
```

---

### 2. Subir o banco de dados

Caso o container do MySQL já esteja criado, execute:

```bash
docker start agendador_mysql
```

Caso utilize Docker Compose, execute:

```bash
docker compose up -d
```

Verifique se o MySQL está disponível na porta:

```text
3306
```

---

### 3. Configurar o banco de dados

Abra o arquivo:

```text
src/main/resources/application.properties
```

Configure as credenciais do banco de dados:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/agendador_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Importante:** em um ambiente de produção, as credenciais não devem ser armazenadas diretamente no código-fonte. Utilize variáveis de ambiente ou um sistema de gerenciamento de secrets.

---

### 4. Executar a aplicação

Utilizando o Maven:

```bash
mvn spring-boot:run
```

Ou execute a aplicação diretamente pela sua IDE, como o IntelliJ IDEA.

A API estará disponível em:

```text
http://localhost:8080
```

---

## 🧪 Endpoints Principais

| Método | Endpoint         | Descrição                |
| ------ | ---------------- | ------------------------ |
| `POST` | `/agendamentos`  | Cria um novo agendamento |
| `GET`  | `/agendamentos`  | Lista os agendamentos    |
| `GET`  | `/clientes`      | Lista os clientes        |
| `GET`  | `/profissionais` | Lista os profissionais   |

### Exemplo de criação de agendamento

```http
POST /agendamentos
Content-Type: application/json
```

```json
{
  "dataHoraAgendamento": "2026-08-14T10:00:00",
  "dataFinalizacao": "2026-08-14T11:00:00"
}
```

---

## 📊 Fluxo de Criação de Agendamento

```text
Cliente
   │
   ▼
POST /agendamentos
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Validação de conflito
   │
   ├── Existe conflito?
   │       │
   │       ├── Sim ──► Rejeita agendamento
   │       │
   │       └── Não
   │
   ▼
Repository
   │
   ▼
MySQL
   │
   ▼
Agendamento criado
```

---

## 🐳 Docker

O banco de dados pode ser executado utilizando Docker para facilitar a configuração do ambiente de desenvolvimento.

Exemplo:

```bash
docker compose up -d
```

Para verificar os containers em execução:

```bash
docker ps
```

Para interromper os containers:

```bash
docker compose down
```

---

## 💡 Roadmap

* [ ] Implementar autenticação com **Spring Security**
* [ ] Implementar autorização baseada em usuários e permissões
* [ ] Implementar autenticação utilizando **JWT**
* [ ] Criar testes unitários com **JUnit 5**
* [ ] Criar testes de integração
* [ ] Implementar documentação da API com **Swagger/OpenAPI**
* [ ] Implementar tratamento global de exceções
* [ ] Implementar DTOs para entrada e saída de dados
* [ ] Implementar validações utilizando **Bean Validation**
* [ ] Integrar **n8n** para automação de processos
* [ ] Implementar notificações automáticas via WhatsApp
* [ ] Implementar notificações automáticas por e-mail
* [ ] Criar pipeline de **CI/CD**
* [ ] Deploy da aplicação em ambiente cloud

---

## 📌 Objetivos Técnicos

Este projeto tem como objetivo demonstrar conhecimentos práticos em:

* Desenvolvimento de APIs REST com Java.
* Spring Boot.
* Spring Data JPA.
* Hibernate.
* MySQL.
* Arquitetura em camadas.
* Implementação de regras de negócio.
* Validação de conflitos de agenda.
* Containerização com Docker.
* Gerenciamento de dependências com Maven.
* Boas práticas de desenvolvimento backend.

---

## 👨‍💻 Autor

Desenvolvido por **Lucas de Lacerda**.

### Tecnologias

`Java` · `Spring Boot` · `Spring Data JPA` · `Hibernate` · `MySQL` · `Docker` · `Maven`
