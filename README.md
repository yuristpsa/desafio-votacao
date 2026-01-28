# Desafio Voting API

API reativa para gerenciamento de pautas, sessões de votação e votos, desenvolvida com Spring WebFlux.

## Sumário
- [Visão Geral](#visão-geral)
- [Tecnologias](#tecnologias)
- [Como Executar](#como-executar)
- [Estrutura dos Endpoints](#estrutura-dos-endpoints)
- [Exemplos de Uso](#exemplos-de-uso)
- [OpenAPI](#openapi)

## Visão Geral
Esta API permite:
- Criar e consultar pautas
- Criar e consultar sessões de votação
- Registrar votos
- Consultar contagem de votos por pauta

## Tecnologias
- Java 17+
- Spring Boot 3+
- Spring WebFlux
- R2DBC (banco reativo)
- Log4j2
- OpenAPI (Swagger)

## Como Executar
1. Clone o repositório
2. Configure o banco de dados em `src/main/resources/application.yaml`
3. Execute:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Acesse a API em `http://localhost:8080`

## Estrutura dos Endpoints

### Pauta
- **Criar pauta**
  - `POST /api/1.0/pautas`
  - Body: `{ "titulo": "string", "descricao": "string" }`
- **Buscar pauta por ID**
  - `GET /api/1.0/pautas/{id}`
- **Listar pautas**
  - `GET /api/1.0/pautas`

### Sessão de Votação
- **Criar sessão**
  - `POST /api/1.0/sessao-votacao`
  - Body: `{ "pautaId": 1, "inicio": "2026-01-28T10:00:00", "minutosAtiva": 10 }`
- **Buscar sessão por ID**
  - `GET /api/1.0/sessao-votacao/{id}`
- **Listar sessões**
  - `GET /api/1.0/sessao-votacao`

### Voto
- **Registrar voto**
  - `POST /api/1.0/votos`
  - Body: `{ "pautaId": 1, "associadoId": "user123", "voto": true }`
- **Buscar voto por ID**
  - `GET /api/1.0/votos/{id}`
- **Listar votos**
  - `GET /api/1.0/votos`
- **Contar votos por pauta**
  - `GET /api/1.0/votos/contagem/{pautaId}`
  - Response: `{ "pautaId": 1, "totalVotos": 10, "totalVotosSim": 6, "totalVotosNao": 4, "vencedor": "SIM" }`

## Exemplos de Uso

### Criar Pauta
```bash
curl -X POST http://localhost:8080/api/1.0/pautas \
  -H 'Content-Type: application/json' \
  -d '{ "titulo": "Nova Pauta", "descricao": "Descrição da pauta" }'
```

### Criar Sessão de Votação
```bash
curl -X POST http://localhost:8080/api/1.0/sessao-votacao \
  -H 'Content-Type: application/json' \
  -d '{ "pautaId": 1, "inicio": "2026-01-28T10:00:00", "minutosAtiva": 10 }'
```

### Registrar Voto
```bash
curl -X POST http://localhost:8080/api/1.0/votos \
  -H 'Content-Type: application/json' \
  -d '{ "pautaId": 1, "associadoId": "user123", "voto": true }'
```

### Contar Votos por Pauta
```bash
curl http://localhost:8080/api/1.0/votos/contagem/1
```

## OpenAPI
O arquivo [openapi.yaml](./openapi.yaml) contém a especificação completa dos endpoints e modelos da API.

## Observações
- Todos os endpoints aceitam e retornam JSON.
- Os logs da aplicação são gravados via Log4j2.
- Para dúvidas ou sugestões, consulte o código-fonte ou o arquivo OpenAPI.

