# NexFleet - documentação arquitetural da Parte 2

## 1. Objetivo e escopo

O NexFleet centraliza a operação de uma frota. O produto completo prevê autenticação, usuários, veículos, motoristas, vínculos, abastecimentos, manutenções e indicadores. Esta entrega implementa a primeira fatia funcional do backend: autenticação por JWT e criação de usuários por um administrador.

O recorte mantém o projeto executável e evita simular funcionalidades que ainda dependem de decisões de negócio. Os módulos de frota e operações permanecem no planejamento das próximas sprints.

## 2. Restrições

- Java 21 e Spring Boot 4.1.1;
- API REST em `/api/v1`;
- PostgreSQL 18 e Flyway;
- Spring Security, JWT e BCrypt;
- Docker Compose para execução local;
- um único backend implantável no MVP;
- regras de domínio sem dependência de Spring ou JPA.

## 3. Contexto

O navegador chama a API NexFleet por HTTPS e JSON. A API valida identidade e autorização antes de executar casos de uso. Somente a API acessa o PostgreSQL. Nesta entrega não há integrações externas nem publicação em Azure.

## 4. Estratégia da solução

O backend usa três decisões complementares:

1. **Monólito modular:** uma aplicação Java com módulos de negócio explícitos.
2. **Vertical Slice:** os arquivos são agrupados pelo caso de uso, como `auth/login` e `usuarios/criar`.
3. **Clean Architecture:** dentro de cada fatia, HTTP e banco ficam nas bordas; aplicação e domínio permanecem no centro.

SOLID orienta as responsabilidades e dependências. O objetivo não é criar uma interface para toda classe, mas proteger os pontos que variam ou conectam detalhes externos.

## 5. Blocos de construção

### Módulo `auth`

- `login/api`: contrato HTTP do login;
- `login/application`: fluxo de autenticação e portas de senha/token;
- `infrastructure/security`: verificação BCrypt e emissão JWT.

### Módulo `usuarios`

- `criar/api`: endpoint de criação;
- `criar/application`: caso de uso, comando, resultado e portas;
- `domain`: entidade `Usuario` e enum `Perfil`;
- `publicapi`: contrato mínimo usado pelo módulo de autenticação;
- `infrastructure`: JPA, Spring Data, BCrypt e bootstrap do administrador.

### Módulo `shared`

Contém configuração de segurança, composição dos casos de uso, identificação da API e tratamento uniforme de erros. Não contém regras de negócio de usuário.

## 6. Visão de execução

### Login

1. O cliente envia e-mail e senha a `POST /api/v1/auth/login`.
2. O controller converte o JSON em `LoginCommand`.
3. `LoginService` consulta o contrato público de usuários.
4. O adaptador BCrypt compara a senha com o hash.
5. O emissor JWT produz um token com identificador, e-mail e perfil.
6. O controller devolve o token Bearer e sua validade.

### Criação de usuário

1. O cliente envia o JWT no cabeçalho `Authorization`.
2. Spring Security valida assinatura e expiração.
3. Somente `ADMINISTRADOR` acessa `POST /api/v1/usuarios`.
4. `CriarUsuarioService` normaliza e-mail, verifica duplicidade e valida a senha.
5. A porta `PasswordHasher` protege a senha com BCrypt.
6. O repositório persiste o usuário e a API retorna `201 Created`.

## 7. Implantação

O Compose executa a API e o PostgreSQL. Configurações sensíveis entram por variáveis de ambiente. O administrador inicial só é criado quando `NEXFLEET_ADMIN_PASSWORD` está definida. A futura publicação em Azure continua planejada, mas não integra esta entrega local.

## 8. Conceitos transversais

- autorização no backend, sem confiar em botões escondidos no frontend;
- JWT curto e sessão stateless;
- senha nunca persistida em texto;
- e-mail normalizado e único também no banco;
- DTOs HTTP separados da entidade de domínio e da entidade JPA;
- erros no formato `ProblemDetail`;
- relógio injetado nos casos de uso para testes determinísticos;
- migrações Flyway imutáveis depois de aplicadas.

## 9. Decisões arquiteturais

### Clean Architecture + Vertical Slice

Aceita. A fatia concentra os arquivos que mudam juntos; a direção de dependência mantém os detalhes externos substituíveis.

### SOLID aplicado

| Princípio | Evidência no código |
|---|---|
| SRP | controllers traduzem HTTP; services coordenam; domínio valida invariantes; adapters integram tecnologia |
| OCP | outra estratégia de token ou senha pode implementar as portas existentes |
| LSP | fakes dos testes e adapters reais preservam o mesmo contrato |
| ISP | `PasswordHasher`, `PasswordVerifier`, `TokenIssuer` e `BuscarUsuarioPorEmail` expõem somente a operação necessária |
| DIP | os casos de uso dependem de interfaces; JPA, BCrypt e JWT implementam essas interfaces |

### Estilos não adotados agora

Serverless, Microfrontend, BFF separado, microsserviços com banco por serviço, API Gateway e EDA distribuída permanecem como opções futuras. O tamanho atual do produto não justifica múltiplos deploys, rede distribuída ou consistência eventual. Eventos internos podem entrar quando mais de um módulo precisar reagir a uma ação de negócio.

## 10. Requisitos de qualidade e testes

Os testes unitários cobrem normalização, validação, duplicidade, proteção de senha, credenciais inválidas e usuário inativo. Quatro testes ArchUnit garantem:

- domínio sem Spring/JPA;
- aplicação sem dependência de API/infraestrutura;
- API sem acesso direto à infraestrutura;
- módulos sem ciclos.

Resultado verificado em Java 21: 12 testes, sem falhas ou erros.

## 11. Riscos e dívidas técnicas

- o fluxo integrado com PostgreSQL precisa ser validado em uma máquina com Docker;
- rotação e revogação de JWT ainda não fazem parte do MVP;
- recuperação de senha e refresh token permanecem fora do escopo;
- veículos, motoristas e abastecimentos exigem as próximas fatias;
- observabilidade ainda se limita ao Actuator e logs padrão.

## 12. Glossário

- **Adapter:** implementação que conecta o núcleo a HTTP, banco ou segurança.
- **Caso de uso:** ação do sistema coordenada pela camada de aplicação.
- **Porta:** interface definida para evitar dependência de um detalhe externo.
- **Slice:** conjunto ponta a ponta de arquivos de uma funcionalidade.
- **JWT:** token assinado usado para transportar a identidade autenticada.

