# URL Shortener API

## Descrição

Esta API REST fornece um serviço de encurtamento de URLs, convertendo URLs longas em identificadores curtos e fáceis de compartilhar. O sistema gera uma versão reduzida da URL utilizando apenas letras e números, com comprimento variável entre 5 e 10 caracteres. URLs encurtadas são armazenadas no banco de dados com um prazo de validade definido.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para construção da API REST.
- **Lombok (@Slf4j)**: Utilizado para geração de logs.
- **Swagger (Springdoc OpenAPI)**: Documentação interativa da API.
- **Spring Boot Actuator**: Exposição de métricas e endpoints de monitoramento.
- **Integração Swagger + Actuator**: Facilita observabilidade e testes em tempo real.
- **H2 Database**: Banco de dados em memória usado para persistência temporária durante o desenvolvimento e testes.
- **Docker** – criação, implantação e gerenciamento de aplicações dentro de contêineres.
- **JUnit 5 + Mockito** – Testes Unitarios


## Requisitos

- Java 21+
- Maven

## Executando o Projeto

1. Clone o repositório:

```bash
git https://github.com/bispobr/Spring-encurtador-url.git
```


## Como usar

1. Inicie a aplicação
2. A API está acessível através do endereço http://localhost:8080
3. A documentação da API está acessível através do Link http://localhost:8080/swagger-ui/index.html#/
4. O endpoint de saúde e métricas do Actuator está acessível através do Link http://localhost:8080/actuator/health


## Como Rodar em um Container (Opcional)

1. Construa o projeto

```bash
mvn clean package 
```

2. Gere a Imagem Docker, com o Docker  instalado execute:


```bash
docker build -t url . 
```

3. Execute o Container

```bash
docker run -p 8080:8080 url
```


## API Endpoints

 API contem o seguinte endpoint :

```http request
POST /shorten-url - Registra uma nova URL.
Content-Type: application/json

{
  "url": "https://www.teste.net/"
}
```
| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `url` | `String` | **Obrigatório**. A url a ser encurtado 

```http request
GET /{encurtamento} - Redireciona para a URL original .

```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `url` | `encurtamento` | **Obrigatório**. Url encurtada, retornada após o uso do metodo post 







