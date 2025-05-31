
# Projeto API Usuários

## Descrição
API REST para gerenciamento de usuários, construída com Spring Boot, Maven e RabbitMQ.

## Tecnologias usadas
- Java 17+
- Spring Boot
- Maven
- RabbitMQ
- Docker & Docker Compose

## Como rodar o projeto localmente

### Requisitos
- Java JDK 17 ou superior instalado
- Maven instalado
- RabbitMQ rodando localmente (ou via Docker)
- Banco de dados configurado (ver `src/main/resources/application.properties`)

### Passos
1. Clone o repositório ou extraia o ZIP:

   ```bash
   git clone <url-do-repositório>
   cd projetoApiUsuarios
   ```

2. Configure o banco de dados e RabbitMQ no arquivo `src/main/resources/application.properties`.

3. Rode o comando para compilar e iniciar a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```

4. A API estará disponível no endereço padrão:

   ```
   http://localhost:8080
   ```

## Como rodar via Docker

### Requisitos
- Docker instalado
- Docker Compose instalado

### Passos

1. Dentro da pasta do projeto, rode:

   ```bash
   docker-compose up --build
   ```

2. Isso iniciará a aplicação e o RabbitMQ via container Docker.

3. A API estará disponível em:

   ```
   http://localhost:8080
   ```

## Endpoints principais (exemplo)

- `POST /usuarios` - Criar usuário
- `POST /usuarios/autenticar` - Autenticar usuário
- `GET /usuarios` - Listar usuários

(Consulte o Swagger se estiver configurado, geralmente em `/swagger-ui.html`)

## Notas importantes

- Ajuste o arquivo `application.properties` para apontar o banco de dados correto e outras configurações.
- RabbitMQ deve estar acessível para o envio/consumo de mensagens.
- Docker Compose já traz um container para o RabbitMQ.

---

Se precisar de ajuda com configurações específicas, só avisar!
