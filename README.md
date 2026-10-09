# NexFleet - TDE 2

Backend acadêmico do NexFleet organizado como monólito modular, com Clean Architecture dentro dos módulos e Vertical Slice por caso de uso.

Branch da entrega: `feature/vertical-slice-clean-solid`.

## Escopo implementado

- login com e-mail e senha;
- senhas protegidas com BCrypt;
- emissão e validação de JWT;
- criação do administrador inicial por variáveis de ambiente;
- criação de usuários restrita ao perfil `ADMINISTRADOR`;
- persistência PostgreSQL versionada por Flyway;
- testes unitários das regras e testes de arquitetura com ArchUnit.

O recorte corresponde à Sprint 1. Veículos, motoristas, abastecimentos, manutenções e dashboard permanecem nas próximas fatias porque dependem de regras de negócio ainda não implementadas.

## Estrutura

```text
backend/src/main/java/br/com/nexfleet
├── auth/login
│   ├── api
│   └── application
├── auth/infrastructure/security
├── usuarios/criar
│   ├── api
│   └── application
├── usuarios/domain
├── usuarios/publicapi
├── usuarios/infrastructure
└── shared
```

As dependências seguem a direção `api -> application -> domain`. A infraestrutura implementa as portas definidas pela aplicação ou pelo contrato público do módulo.

## Executar localmente

1. Copie `.env.example` para `.env` e troque todas as senhas.
2. Execute `docker compose up --build`.
3. Verifique `GET http://localhost:8080/actuator/health`.
4. Autentique em `POST http://localhost:8080/api/v1/auth/login`.
5. Use o token Bearer para chamar `POST http://localhost:8080/api/v1/usuarios`.

Exemplo de login:

```json
{
  "email": "admin@nexfleet.local",
  "senha": "a-senha-definida-no-env"
}
```

Exemplo de criação:

```json
{
  "nome": "Gestor da Frota",
  "email": "gestor@nexfleet.local",
  "senha": "uma-senha-com-8-ou-mais-caracteres",
  "perfil": "GESTOR_FROTA"
}
```

## Testes

No diretório `backend`, execute:

```text
mvn test
```

## Documentação da entrega

- [Arquitetura — Parte 2](docs/ARQUITETURA_PARTE2.md)
- [Participação dos alunos](docs/CONTRIBUICOES.md)
- [Prompts utilizados](docs/PROMPTS_UTILIZADOS.md)
- [Diagrama de classes](docs/diagramas/diagrama-classes-backend.md)
- [Diagrama de componentes](docs/diagramas/diagrama-componentes-backend.md)

