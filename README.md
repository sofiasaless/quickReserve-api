
# QuickReserve API

Esta é uma API RESTful desenvolvida para gerenciar reservas de mesas em restaurantes de forma prática, rápida e eficiente. Seu principal objetivo é facilitar o processo de reserva tanto para os clientes quanto para os restaurentes, oferecendo uma solução moderna e escalável para controle de disponibilidade, agendamentos e gerenciamento de mesas.

A API foi construída com Java e Spring Boot, seguindo os princípios de arquitetura limpa e boas práticas de desenvolvimento. Com suporte a integração via Docker, o projeto está preparado para ambientes de produção e fácil implantação.


## Instalação

### Requisitos Básicos

Antes de tudo, você precisa ter instalado:

- Java 17 ou superior  
- Maven 3.6+  
- Git  
- Docker  

### ⚙Execução

- 1. Clone o repositório
```bash
  git clone https://github.com/sofiasaless/quickReserve-api
```

- 2. Navegue até o diretório e execute o container docker
```bash
  cd quickReserve-api
  docker-compose up
```

- 3. Instale as depedências e execute o projeto com o Maven
```bash
  mvn clean install
  mvn spring-boot:run
```

## Tecnologias Utilizadas

### Código
- **Java 17** — Linguagem principal da aplicação
- **Spring Boot** — Framework para criação da API REST
- **Spring Web** — Criação dos endpoints REST
- **Spring Data JPA** — Abstração de persistência com Hibernate
- **Spring Validation** — Validação automática de dados via anotações
- **Spring Security** — Controle de autenticação e autorização da aplicação
- **JWT (JSON Web Token)** — Geração e validação de tokens de autenticação
- **Lombok** — Redução de boilerplate no código Java

### Banco de Dados
- **PostgreSQL** — Banco de dados relacional utilizado na aplicação

### Infraestrutura
- **Docker & Docker Compose** — Contêineres para facilitar o setup do ambiente

### Gerenciamento de Projeto
- **Maven** — Gerenciador de dependências e build
## Uso

Acesse a API em [http://localhost:8080](http://localhost:8080)

## End-points

### Rotas públicas

#### Criação de usuários clientes e restaurantes

```bash
POST - http://localhost:8080/cliente/cadastrar
POST - http://localhost:8080/restaurante/cadastrar
```

```json
{
    "nome":"Cloroquina Silveira",
    "cpf":"101.225.750-91",
    "email":"cloroq@gmail.com",
    "dataAniversario":"2021-09-27",
    "senha":"1234"
}
```

```json
{
    "nome":"Tocanto Quixadá",
    "cnpj":"87.760.716/0001-75",
    "email":"tocantoq@gmail.com",
    "descricao":"Tocanto Quixadá é um restaurante gourmet que oferece uma diversidade de pratos executivos e massas deliciosas.",
    "imagemPerfil":"https://st2.depositphotos.com/3742757/6877/v/450/depositphotos_68774435-stock-illustration-vector-modern-flat-design-illustration.jpg",
    "tipoRestaurante":"RESTAURANTE",
    "senha":"1234"
}
```

#### Login de usuários clientes e restaurantes

```bash
POST - http://localhost:8080/entrar/cliente
POST - http://localhost:8080/entrar/restaurante
```

```json
{
	"email":"sofiasaleswk@gmail.com",
	"senha":"1234"
}
```

```json
{
	"email":"tocantoq@gmail.com",
	"senha":"1234"
}
```

##### Resposta (em caso de 200 OK)

```json
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ1cGJ1c2luZXNzIiwiZXhwIjoxNzQ0MTY0OTUzLCJzdWIiOiIzIiwicm9sZXMiOlsiQ0xJRU5URSJdfQ.RoaFq3awL58qt-thmzweLZt3lqwXcsY6-qgpfrlF6mw"
}
```

#### Listagem de mesas de um restaurante

```bash
GET - http://localhost:8080/restaurante/mesas/{id_restaurante}
GET - http://localhost:8080/restaurante/mesas/4
```

##### Resposta (em caso de id do restaurante ser válido)
```json
[
	{
		"id": 12,
		"numero": 100,
		"restauranteEntity": null,
		"restauranteId": 4,
		"criadoEm": "2025-01-18T00:20:11.916692"
	},
	{
		"id": 13,
		"numero": 101,
		"restauranteEntity": null,
		"restauranteId": 4,
		"criadoEm": "2025-01-18T00:24:58.909331"
	}
]
```


### Rotas protegidas - Cliente

#### Visualização de perfil

```bash
GET - http://localhost:8080/cliente/perfil
Authorization: Bearer Token
```

##### Resposta

```json
{
	"id": 3,
	"nome": "Antonio Widney Lima",
	"cpf": "787.732.750-18",
	"email": "widneylima@gmail.com",
	"dataAniversario": "2003-08-02",
	"criadoEm": "2025-01-10T20:59:09.877596"
}
```

#### Exclusão de perfil

```bash
DELETE - http://localhost:8080/cliente/deletar
Authorization: Bearer Token
```

#### Solicitação de reserva feita por cliente ao restaurante
```bash
POST - http://localhost:8080/reservas/cliente/nova-reserva
Authorization: Bearer Token
```

```json
{
	"mesaId":8,
	"dataParaReserva":"2025-04-11"
}
```

##### Resposta (em caso de 200 OK)
```json
{
	"id": 17,
	"mesaEntity": null,
	"mesaId": 8,
	"clienteEntity": null,
	"clienteId": 3,
	"dataParaReserva": "2025-04-11",
	"statusReserva": "PENDENTE",
	"criadoEm": "2025-04-08T21:22:11.896692"
}
```

#### Listagem reservas de um cliente

```bash
GET - http://localhost:8080/reservas/cliente/listarTodas
Authorization: Bearer Token
```
##### Resposta

```json
[
	{
		"id": 7,
		"mesaEntity": {
			"id": 11,
			"numero": 80,
			"restauranteEntity": {
				"id": 3,
				"nome": "Broiler Churrasco",
				"descricao": null,
				"tipoRestaurante": null,
				"imagemPerfil": null,
				"cnpj": "18.003.362/0001-00",
				"email": "broiler@outlook.com",
				"senha": "$2a$10$g8JaxtIzVrrAuJrVEZ9TeO64wom3Pxp.izBiTMWyaSg99oNoP6oju",
				"criadoEm": "2025-01-10T20:57:22.640501"
			},
			"restauranteId": 3,
			"criadoEm": "2025-01-10T21:09:26.090873"
		},
		"mesaId": 11,
		"clienteEntity": {
			"id": 3,
			"nome": "Antonio Widney Lima",
			"cpf": "787.732.750-18",
			"email": "widneylima@gmail.com",
			"senha": "$2a$10$g8JaxtIzVrrAuJrVEZ9TeO64wom3Pxp.izBiTMWyaSg99oNoP6oju",
			"dataAniversario": "2003-08-02",
			"criadoEm": "2025-01-10T20:59:09.877596"
		},
		"clienteId": 3,
		"dataParaReserva": "2025-01-15",
		"statusReserva": "PENDENTE",
		"criadoEm": "2025-03-19T22:28:05.960223"
	},
	{
		"id": 17,
		"mesaEntity": {
			"id": 8,
			"numero": 11,
			"restauranteEntity": {
				"id": 2,
				"nome": "Q-pao Conceito",
				"descricao": null,
				"tipoRestaurante": null,
				"imagemPerfil": null,
				"cnpj": "61.287.328/0001-25",
				"email": "qpaoconc@gmail.com",
				"senha": "$2a$10$g8JaxtIzVrrAuJrVEZ9TeO64wom3Pxp.izBiTMWyaSg99oNoP6oju",
				"criadoEm": "2025-01-09T00:36:58.37423"
			},
			"restauranteId": 2,
			"criadoEm": "2025-01-09T00:37:38.093574"
		},
		"mesaId": 8,
		"clienteEntity": {
			"id": 3,
			"nome": "Antonio Widney Lima",
			"cpf": "787.732.750-18",
			"email": "widneylima@gmail.com",
			"senha": "$2a$10$g8JaxtIzVrrAuJrVEZ9TeO64wom3Pxp.izBiTMWyaSg99oNoP6oju",
			"dataAniversario": "2003-08-02",
			"criadoEm": "2025-01-10T20:59:09.877596"
		},
		"clienteId": 3,
		"dataParaReserva": "2025-04-11",
		"statusReserva": "PENDENTE",
		"criadoEm": "2025-04-08T21:22:11.896692"
	}
]
```

#### Atualizar reserva
```bash
PUT - http://localhost:8080/reservas/cliente/atualizar/7
Authorization: Bearer Token
```

```json
{
	"mesaId":11,
	"dataParaReserva":"2025-01-20"
}
```

### Rotas protegidas - Restaurante

#### Visualização de perfil

```bash
GET - http://localhost:8080/restaurante/perfil
Authorization: Bearer Token
```

##### Resposta (em caso de 200 OK)
```json
{
	"id": 10,
	"nome": "Tocanto Quixadá",
	"cnpj": "87.760.716/0001-75",
	"email": "tocantoq@gmail.com",
	"imagemPerfil": "https://st2.depositphotos.com/3742757/6877/v/450/depositphotos_68774435-stock-illustration-vector-modern-flat-design-illustration.jpg",
	"descricao": "Tocanto Quixadá é um restaurante gourmet que oferece uma diversidade de pratos executivos e massas deliciosas.",
	"tipoRestaurante": "RESTAURANTE",
	"criadoEm": "2025-03-22T18:40:31.649248"
}
```

#### Exclusão de perfil

```bash
DELETE - http://localhost:8080/restaurante/deletar
Authorization: Bearer Token
```

#### Listagem reservas pendentes, canceladas e confirmadas de um restaurante
```bash
GET - http://localhost:8080/reservas/restaurante/listar?status={status_reservas}
GET - http://localhost:8080/reservas/restaurante/listar?status=pendente
Authorization: Bearer Token
```

##### Resposta (em caso de 200 OK)
```json
[
	{
		"id": 7,
		"mesaEntity": {
			"id": 11,
			"numero": 80,
			"restauranteEntity": {
				"id": 3,
				"nome": "Broiler Churrasco",
				"descricao": null,
				"tipoRestaurante": null,
				"imagemPerfil": null,
				"cnpj": "18.003.362/0001-00",
				"email": "broiler@outlook.com",
				"senha": "$2a$10$g8JaxtIzVrrAuJrVEZ9TeO64wom3Pxp.izBiTMWyaSg99oNoP6oju",
				"criadoEm": "2025-01-10T20:57:22.640501"
			},
			"restauranteId": 3,
			"criadoEm": "2025-01-10T21:09:26.090873"
		},
		"mesaId": 11,
		"clienteEntity": {
			"id": 3,
			"nome": "Antonio Widney Lima",
			"cpf": "787.732.750-18",
			"email": "widneylima@gmail.com",
			"senha": "$2a$10$g8JaxtIzVrrAuJrVEZ9TeO64wom3Pxp.izBiTMWyaSg99oNoP6oju",
			"dataAniversario": "2003-08-02",
			"criadoEm": "2025-01-10T20:59:09.877596"
		},
		"clienteId": 3,
		"dataParaReserva": "2025-01-15",
		"statusReserva": "PENDENTE",
		"criadoEm": "2025-03-19T22:28:05.960223"
	}
]
```

#### Atualizar reserva
```bash
PUT - http://localhost:8080/reservas/restaurante/atualizar
Authorization: Bearer Token
```

```json
{
	"id":8,
	"statusReserva":"CANCELADA"
}
```

## Licença

[MIT](https://choosealicense.com/licenses/mit/)

