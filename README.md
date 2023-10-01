# Teste Técnico - Gerenciamento de Pessoas API

Este repositório contém uma aplicação Spring Boot que implementa uma API para gerenciar pessoas e seus endereços. A API oferece as seguintes funcionalidades:

- Criar uma pessoa
- Editar uma pessoa
- Consultar uma pessoa
- Listar pessoas
- Criar endereço para pessoa
- Listar endereços da pessoa
- Definir endereço principal da pessoa

## Funcionalidades

### Pessoa

Uma pessoa possui os seguintes campos:

- Nome
- Data de nascimento

### Endereço

Um endereço é composto por:

- Logradouro
- CEP
- Número
- Cidade

## Requisitos

- Todas as respostas da API são no formato JSON
- Utilização do banco de dados H2
- Implementação de testes unitários
- Adoção de Clean Code

## Estrutura do Projeto

A estrutura do projeto segue o padrão Maven com as seguintes principais packages:

- `com.github.fsousa1987.attornatus`: Contém a classe de inicialização da aplicação e demais pacotes.
- `com.github.fsousa1987.attornatus.api`: Pacote que contém os controllers, as exceptionhandler, request e response.
- `com.github.fsousa1987.attornatus.core`: Define o mapper da aplicação (MapStruct).
- `com.github.fsousa1987.attornatus.domain`: Contém as entidades, repositórios e o serviço.

## Boas Práticas

O código segue boas práticas de programação, incluindo:

- Uso de princípios SOLID
- Aplicação de padrões de design quando apropriado
- Nomenclatura descritiva e significativa
- Comentários explicativos onde necessário

## Executando a Aplicação

1. Clone o repositório em sua máquina local.
2. Abra o projeto em sua IDE preferida.
3. Configure o ambiente para rodar um aplicativo Spring Boot.
4. Execute a aplicação.

## Testes

Para rodar os testes unitários, utilize o comando Maven:

mvn test

## Autor

[Francisco Edglei de Sousa]

- Continuarei comitando coisas novas nesse projeto, mesmo se não conseguir passar no processo seletivo. Penso que será ótimo para estudos.
- Agradeço se algum avaliador chegou até aqui e agradeço a oportunidade

## Features Futuras (Não necessariamente seguirei a ordem abaixo)

- Adição de autenticação e autorização utilizando OAuth 2.0 e Keycloak
- Adição do Swagger para documentação das APIs
- Publicação na nuvem AWS
