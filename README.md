# 🅿️ Parking – Sistema de Estacionamento

Este projeto foi desenvolvido para um **teste técnico de avaliação** para demonstrar domínio sobre **Kotlin com Spring Boot**, arquitetura em camadas, integração com banco de dados via JDBC e JPA, utilização de Flyway para versionamento de schema, boas práticas de organização de código e testes unitários.

---

## 📌 Objetivo do Projeto

A aplicação simula o controle de um estacionamento, com funcionalidades como:

- Entrada e saída de veículos
- Alocação dinâmica de vagas
- Cálculo de cobrança com base na lotação atual
- Consulta de status por placa ou localização
- Relatórios de receita por setor e data

---

## ⚙️ Tecnologias e Bibliotecas

- **Java 21**
- **Kotlin 1.9.25**
- **Spring Boot 3.5.5**
- **Spring Web, JDBC, Data JPA**
- **Flyway** – versionamento do banco de dados
- **H2 Database** – usado para testes e dev local
- **PostgreSQL** – ambiente de produção
- **Swagger / OpenAPI** – documentação automática
- **JUnit 5 + Mockito** – testes unitários e de integração

---

## 🗂️ Estrutura dos Pacotes

```text
com.br.park
├── api                     # Controllers, Requests e Reponses
├── config                  # Configurações de contexto, H2, startup
├── infrastructure
    └── client              # Client HTTP para consumo das configurações da garagem
│   └── repository          # Repositórios e entidades (JPA / JDBC)
├── mapper                  # Conversores entre Entidades
├── service                 # Regras de negócio
│   ├── webhook             # Estratégias baseadas em tipo de evento
│   ├── calculator          # Lógica de precificação
│   └── changefactory       # Factory para preço dinâmico por lotação
└── shared                  # Classes compartilhadas para a aplicação
    └── enums               # Enums da aplicação
    └── error               # Classes de erro para tratar exceptions no app
```

---

## ▶️ Como Rodar Localmente

### Pré-requisitos:
- Java 21+
- Maven 3.8+
- PostgreSQL (opcional)
- Docker (opcional)

### Passos:

```bash
# 1. Clone o repositório
git clone https://github.com/FelipeBatistaDev/parking.git

cd parking

# 2. Rode os testes
mvn test

# 3. Rode o projeto local **(precisa estar com o simulador de garagem acessivel)**
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 4. Rode o projeto docker **(precisa estar com o simulador de garagem acessivel)**
docker compose up --build

```
## 📈 Documentação da API

A aplicação rodará com a documentaçao
em: `http://localhost:3003/swagger-ui.html`


## 🧪 Testes

O projeto inclui testes:

- **Unitários**: para services e cálculo de valores com `Mockito`

### Rodar testes:

```bash
mvn test
```

---

## 🧠 Estratégias Arquiteturais

- **Separação por responsabilidade (SRP)**: camadas bem definidas
- **Resolução dinâmica via ApplicationContext**: aplicada para eventos de webhook
- **Flyway**: versionamento e evolução controlada do schema
- **Testes em todas as camadas críticas**

---

## 🚀 Diferenciais do Projeto

- ✅ Padrões de projeto aplicados (Strategy, Factory)
- ✅ Estrutura limpa e escalável
- ✅ Cálculo de preço desacoplado da entidade
- ✅ Extensível para novos tipos de evento sem impacto no controller
- ✅ Configurações isoladas por ambiente (`application-dev.yml`, `application-docker.yml`)

---


## 📄 Autor

Desenvolvido por **Felipe** como parte de um processo seletivo.

---
