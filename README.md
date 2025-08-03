# Projeto API Usuários

## Visão Geral da Arquitetura

```

src/
└── main/
    └── java/
        └── br/
            └── com/
                └── cotiinformatica/
                    ├── components/              # Componentes auxiliares (JWT, Criptografia, Email, RabbitMQ)
                    │   ├── JwtBearerComponent.java
                    │   ├── RabbitMQPublisherComponent.java
                    │   ├── RabbitMQWorkerComponent.java
                    │   ├── SHA256Component.java
                    │   └── SmtpMailComponent.java
                    ├── configurations/          # Configurações gerais da aplicação (Swagger, ModelMapper, RabbitMQ)
                    │   ├── ModelMapperConfiguration.java
                    │   ├── RabbitMQConfiguration.java
                    │   └── SwaggerConfiguration.java
                    ├── controllers/             # Controladores REST que recebem e processam requisições HTTP
                    │   └── UsuariosController.java
                    ├── dtos/                    # Data Transfer Objects para comunicação entre camadas
                    │   ├── AutenticarUsuarioRequest.java
                    │   ├── AutenticarUsuarioResponse.java
                    │   ├── CriarUsuarioRequest.java
                    │   └── CriarUsuarioResponse.java
                    ├── entities/                # Entidades JPA que representam tabelas do banco de dados
                    │   └── Usuario.java
                    ├── events/                  # Eventos de domínio para ações como criação de usuário
                    │   └── UsuarioCriadoEvent.java
                    ├── handlers/                # Tratadores de exceções
                    │   ├── IllegalArgumentExceptionHandler.java
                    │   └── ValidationHandler.java
                    ├── repositories/            # Interfaces JPA para acesso e manipulação dos dados no banco
                    │   └── UsuarioRepository.java
                    ├── services/                # Serviços com lógica de negócio da aplicação
                    │   └── UsuarioService.java
                    └── ProjetoApiUsuariosApplication.java  # Classe principal para inicialização do Spring Boot

```
<img width="1545" height="422" alt="image" src="https://github.com/user-attachments/assets/4801cbea-606b-442c-a39f-0224dafb39e6" />


Este projeto é uma API RESTful construída em Java utilizando o framework Spring Boot. A arquitetura segue os princípios do padrão **Clean Architecture** e **Domain-Driven Design (DDD)** para garantir alta coesão, baixo acoplamento e escalabilidade.

O sistema está organizado em camadas lógicas que separam responsabilidades claras, facilitando manutenção e testes. Além disso, utiliza comunicação assíncrona via RabbitMQ para desacoplar processos e aumentar a robustez.

---

# Estrutura e Camadas do Projeto

## 1. Camada de Apresentação (Controller)

Responsável por expor os endpoints REST da aplicação e atuar como porta de entrada das requisições HTTP.  
**Exemplo:** `usuario.controller.UsuariosController`

- Recebe e valida os dados enviados pelos clientes  
- Encaminha as chamadas para os serviços da camada de negócio  
- Retorna respostas adequadas com uso de DTOs (Request/Response)  
- Facilita a documentação via Swagger  

---

## 2. Camada de Aplicação (Service)

Contém a lógica de negócio e os casos de uso da aplicação.  
**Exemplo:** `usuario.service.UsuarioService`

- Orquestra a execução de regras, integrações e persistência  
- Publica eventos em filas RabbitMQ para processamento assíncrono  
- Realiza transformações entre entidades e DTOs usando ModelMapper  
- Atua como intermediário entre o controller e a camada de dados  

---

## 3. Camada de Persistência (Repository)

Responsável por acessar e manipular os dados no banco utilizando Spring Data JPA.  
**Exemplo:** `usuario.repository.UsuarioRepository`

- Encapsula as operações de CRUD e consultas customizadas  
- Trabalha diretamente com entidades JPA que refletem o modelo do banco de dados  
- Reduz o código boilerplate com repositórios baseados em interfaces  

---

## 4. Camada de Domínio (Entities e Models)

Define as estruturas centrais da aplicação que representam as regras e conceitos do negócio.  
**Exemplo:** `usuario.entity.Usuario`

- Representa as entidades persistidas no banco  
- Contém atributos, relacionamentos e anotações JPA  
- Pode conter validações e regras específicas do domínio  

---

## 5. Camada de Segurança

Centraliza toda a lógica relacionada à autenticação e autorização.  
**Pacotes envolvidos:** `security.jwt`, `security.filters`, `security.config`

- Geração e validação de tokens JWT  
- Filtros que interceptam requisições para validação de autenticação  
- Definição de rotas públicas e protegidas via Spring Security  
- Suporte a autenticação baseada em roles/perfis de usuário  

---

## 6. Comunicação Assíncrona (RabbitMQ)

Implementa integração assíncrona com RabbitMQ para melhorar o desempenho e desacoplamento.  
**Exemplo:** `components.RabbitMQPublisherComponent`

- Permite envio de mensagens/eventos para filas  
- Garante que processos custosos possam ser executados em segundo plano  
- Facilita escalabilidade e resiliência do sistema  

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
- kkaioribeiro@gmail.com
- https://www.linkedin.com/in/kaiomuniz/

---

Obrigado por utilizar o Projeto API Usuários! 🚀
