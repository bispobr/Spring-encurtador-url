# Encurtador de URLs

Este repositório contém um projeto simples desenvolvido com Java Spring, com o objetivo de praticar e aplicar conceitos dessa tecnologia. Nesse projeto é implementar um serviço que permite encurtar URLs longas para torná-las mais compactas e fáceis de
compartilhar. Seguindo os detalhes abaixo

## Requisitos

- O encurtador de URLs recebe uma URL longa como parâmetro inicial.
- O encurtamento será composto por um mínimo de 05 e um máximo de 10 caracteres.
- Apenas letras e números são permitidos no encurtamento.
- A URL encurtada será salva no banco de dados com um prazo de validade.
- Ao receber uma chamada para a URL encurtada `https://xxx.com/DRE856`, será realizado o redirecionamento para a
  URL original salva no banco de dados. Caso a URL não seja encontrada no banco,  o código de
  status `HTTP 404 (Not Found)` será retornado.

## Instalação

1. Clone o repositório:

```bash
git https://github.com/bispobr/Spring-encurtador-url.git
```

2. Instale as dependências com Maven

## Como Usar

1. Inicie a aplicação com o Maven
2. API está acessível através do Link http://localhost:8080

## API Endpoints

A API contem o seguinte endpoint :

```http request
POST /shorten-url - Registra uma nova URL.
Content-Type: application/json

{
  "url": "https://www.teste.net/"
}
```

```http request
GET /url/XZYWZ - Retorna a URL original especificada na requisição.

```







