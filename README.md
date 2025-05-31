# Projeto API Usuários

## Visão Geral da Arquitetura

Este projeto é uma API RESTful construída em Java utilizando o framework Spring Boot. A arquitetura segue os princípios do padrão **Clean Architecture** e **Domain-Driven Design (DDD)** para garantir alta coesão, baixo acoplamento e escalabilidade.

O sistema está organizado em camadas lógicas que separam responsabilidades claras, facilitando manutenção e testes. Além disso, utiliza comunicação assíncrona via RabbitMQ para desacoplar processos e aumentar a robustez.

---

## Estrutura e Camadas do Projeto
src/main/java/br/com/cotiinformatica
│
├── usuario                # Módulo relacionado à gestão de usuários
│   ├── controller         # Controladores REST para requisições HTTP
│   ├── service            # Regras de negócio e serviços
│   ├── repository         # Acesso a dados (DAO)
│   ├── dto                # Data Transfer Objects para entrada e saída
│   ├── entity             # Entidades JPA que mapeiam o banco de dados
│   └── events             # Eventos relacionados ao módulo usuário
│
├── security               # Configurações e componentes de segurança
│   ├── config             # Configurações específicas de segurança
│   ├── jwt                # Classes relacionadas a JWT (tokens)
│   ├── filters            # Filtros de autenticação e autorização
│   └── utils              # Utilitários de segurança
│
├── config                 # Configurações gerais do sistema
│   ├── SwaggerConfig.java     # Configuração da documentação Swagger
│   ├── RabbitMQConfig.java    # Configuração do RabbitMQ
│   └── ModelMapperConfig.java # Configuração do ModelMapper
│
├── exception              # Tratamento global de exceções
│   └── GlobalExceptionHandler.java
│
├── ProjetoApiUsuariosApplication.java  # Classe principal do Spring Boot

### 1. Camada de Apresentação (Controller)

Responsável por expor os endpoints REST e receber as requisições HTTP.  
Exemplo: `usuario.controller.UsuariosController`  

- Validação básica dos dados de entrada
- Invoca serviços da camada de negócio
- Retorna respostas formatadas com DTOs

### 2. Camada de Aplicação (Service)

Contém as regras de negócio e casos de uso.  
Exemplo: `usuario.service.UsuarioService`  

- Orquestra ações de persistência, validação complexa e comunicação com outros componentes
- Envia eventos para filas RabbitMQ para processos assíncronos
- Utiliza DTOs para entrada e saída, mapeados via ModelMapper

### 3. Camada de Persistência (Repository)

Interface com o banco de dados, utilizando Spring Data JPA.  
Exemplo: `usuario.repository.UsuarioRepository`  

- Abstrai operações CRUD e consultas específicas
- Trabalha com entidades JPA que representam tabelas do banco

### 4. Camada de Domínio (Entity / Model)

Representa as entidades do sistema e suas propriedades.  
Exemplo: `usuario.entity.Usuario`  

- Define atributos e relacionamentos do domínio
- Validações e regras específicas podem ser aplicadas aqui

### 5. Camada de Segurança

Isola toda a lógica relacionada à autenticação e autorização, incluindo:

- Geração e validação de tokens JWT (`security.jwt`)
- Filtros e interceptadores de requisição (`security.filters`)
- Configurações de segurança do Spring Security (`security.config`)

### 6. Comunicação Assíncrona com RabbitMQ

- Eventos são publicados na fila RabbitMQ (`components.RabbitMQPublisherComponent`)
- Facilita integração desacoplada e processamento em background
- Permite escalabilidade horizontal do sistema

---

## Fluxo de Autenticação

1. Cliente envia credenciais via endpoint `/usuarios/autenticar`.
2. Serviço valida usuário e gera token JWT.
3. Token é retornado e deve ser enviado em headers nas próximas requisições.
4. Filtros interceptam requisições e validam o token para autorizar o acesso.

---

## Tecnologias e Padrões

- **Spring Boot:** Framework principal para desenvolvimento rápido e produtivo.
- **Spring Data JPA:** Facilita persistência e abstração do banco.
- **ModelMapper:** Conversão entre DTOs e entidades.
- **RabbitMQ:** Broker para mensagens assíncronas e eventos.
- **JWT:** Autenticação baseada em tokens.
- **Swagger:** Documentação interativa da API.
- **Docker & Docker Compose:** Facilita a implantação e ambiente de desenvolvimento replicável.

---

## Instruções Resumidas para Execução

- Ajuste `application.properties` com configurações do banco e RabbitMQ.
- Rode localmente com `./mvnw spring-boot:run`.
- Ou rode via Docker Compose com `docker-compose up --build`.
- Acesse a API em `http://localhost:8080`.
- Utilize Swagger em `http://localhost:8080/swagger-ui.html` para explorar os endpoints.

---

## Boas Práticas Adotadas

- **DTOs** para separar modelo de domínio de dados da API.
- **Eventos** para comunicação desacoplada e maior resiliência.
- **Camadas bem definidas** para facilitar testes e manutenção.
- **Tratamento global de exceções** com respostas padronizadas.
- **Configurações via propriedades** para facilitar mudanças de ambiente.
- **Documentação automatizada** via Swagger.

---

## Contato

Para dúvidas ou sugestões, entre em contato:  
kkaioribeiro@gmail.com
https://www.linkedin.com/in/kaiomuniz/

---

Obrigado por utilizar o Projeto API Usuários! 🚀
