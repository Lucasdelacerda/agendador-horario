# 🚀 Agendador Horário API

> Sistema corporativo de gerenciamento e agendamentos de horários, desenvolvido com foco em alta performance, robustez de regras de negócio e integração com automações.

---

## 📋 Sobre o Projeto

O **Agendador Horário** é uma API REST backend desenvolvida para solucionar problemas reais de prestadores de serviços, clínicas, salões e profissionais autônomos. O grande diferencial do sistema é a **validação inteligente de conflitos de agenda**, impedindo sobreposições de horários de forma automatizada e garantindo integridade total dos dados.

---

## 🛠️ Tecnologias e Ferramentas

Este projeto foi construído utilizando tecnologias modernas e amplamente adotadas pelo mercado corporativo:

*   **Java 21** - Linguagem principal (Modern, Safe & Fast)
*   **Spring Boot 3.x** - Framework principal para construção da API
*   **Spring Data JPA / Hibernate** - Mapeamento objeto-relacional e persistência
*   **MySQL** - Banco de dados relacional
*   **Docker & Docker Compose** - Containerização do ambiente de banco de dados
*   **Lombok** - Redução de código boilerplate
*   *(Em breve)* **Spring Security & JWT** - Autenticação e Autorização segura
*   *(Em breve)* **JUnit 5 & Mockito** - Testes unitários e de integração
*   *(Em breve)* **n8n** - Automação de processos (Webhooks para WhatsApp/E-mail)

---

## ⚙️ Arquitetura e Regras de Negócio

*   **Prevenção de Conflitos (Overlapping):** A API valida algoritmicamente os intervalos de tempo (`dataHoraAgendamento` e `dataFinalizacao`) antes de efetivar qualquer registro no banco, bloqueando choques de horário.
*   **Arquitetura em Camadas:** Organizado de forma limpa separando *Controllers*, *Services*, *Repositories* e *Entities*.

---

## 🚀 Como Rodar o Projeto Localmente

Siga os passos abaixo para executar a aplicação na sua máquina:

### Pré-requisitos
*   Java 21 instalado
*   Docker e Docker Compose rodando
*   Maven

### 1. Clonar o repositório
```bash
git clone [https://github.com/SEU-USUARIO/agendador-horario.git](https://github.com/SEU-USUARIO/agendador-horario.git)
cd agendador-horario/agendador-horario