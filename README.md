# URL Shortener API

API REST desenvolvida com Java e Spring Boot para encurtamento e redirecionamento de URLs.

O serviço recebe uma URL original, gera um identificador curto e armazena a associação entre a URL original e seu identificador. As URLs encurtadas possuem prazo de validade definido pela aplicação.

## Funcionalidades

- Criação de URLs encurtadas
- Geração de identificadores com letras e números
- Identificadores com comprimento variável entre 5 e 10 caracteres
- Armazenamento das URLs encurtadas
- Redirecionamento para a URL original
- Expiração das URLs conforme a configuração da aplicação
- Documentação interativa com Swagger/OpenAPI
- Monitoramento com Spring Boot Actuator
- Testes automatizados com JUnit 5 e Mockito
- Execução em container Docker

## Tecnologias

- Java 21+
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Springdoc OpenAPI
- Spring Boot Actuator
- Lombok
- JUnit 5
- Mockito
- Maven
- Docker

## Requisitos

- Java 21+
- Maven
- Docker (opcional)

## Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/bispobr/Spring-encurtador-url.git
cd Spring-encurtador-url
```

Execute a aplicação com Maven:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Swagger / OpenAPI

Com a aplicação em execução, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

## Actuator

Endpoint de saúde:

```text
http://localhost:8080/actuator/health
```

## API Endpoints

### Criar URL encurtada

```http
POST /shorten-url
Content-Type: application/json
```

Exemplo de requisição:

```json
{
  "url": "https://www.exemplo.com/"
}
```

A aplicação retorna o identificador utilizado para acessar a URL encurtada.

### Redirecionar para a URL original

```http
GET /{encurtamento}
```

O parâmetro `encurtamento` corresponde ao identificador gerado no processo de criação da URL.

| Parâmetro | Tipo | Descrição |
|---|---|---|
| `encurtamento` | `String` | Identificador da URL encurtada. |

Fluxo simplificado:

```text
URL original
     │
     ▼
POST /shorten-url
     │
     ▼
Gerador de identificador
     │
     ▼
URL encurtada
     │
     ▼
GET /{encurtamento}
     │
     ▼
URL original
```

## Persistência

O projeto utiliza **H2 Database** como banco de dados em memória para desenvolvimento e testes.

As informações das URLs encurtadas são persistidas durante a execução da aplicação.

## Testes

Execute os testes com:

```bash
mvn test
```

O projeto utiliza JUnit 5 e Mockito para os testes automatizados.

## Docker

Gere o pacote da aplicação:

```bash
mvn clean package
```

Gere a imagem Docker:

```bash
docker build -t url .
```

Execute o container:

```bash
docker run -p 8080:8080 url
```

## Status

Projeto desenvolvido para praticar a construção de uma API REST com Spring Boot, persistência com JPA, geração de URLs encurtadas, redirecionamento, documentação OpenAPI, monitoramento, testes automatizados e execução em containers.
