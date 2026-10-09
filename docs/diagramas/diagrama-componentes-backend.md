# Diagrama de componentes do backend

As setas acompanham o fluxo de execução. A dependência de código aponta para as portas internas: os adapters implementam interfaces da aplicação ou da API pública do módulo.

```mermaid
flowchart LR
    Cliente[Cliente HTTP]

    subgraph API[Adapters de entrada]
        LoginController[LoginController]
        CriarController[CriarUsuarioController]
        Security[Spring Security JWT]
    end

    subgraph APP[Casos de uso]
        LoginUC[LoginUseCase / LoginService]
        CriarUC[CriarUsuarioUseCase / CriarUsuarioService]
    end

    subgraph CORE[Núcleo]
        Usuario[Usuario + Perfil]
        PublicAPI[BuscarUsuarioPorEmail]
        Ports[UsuarioRepository\nPasswordHasher\nPasswordVerifier\nTokenIssuer]
    end

    subgraph INFRA[Adapters de saída]
        Jpa[JpaUsuarioRepositoryAdapter]
        BCrypt[BCrypt adapters]
        JWT[JwtTokenIssuer]
        Flyway[Flyway]
    end

    DB[(PostgreSQL)]

    Cliente --> Security
    Security --> LoginController
    Security --> CriarController
    LoginController --> LoginUC
    CriarController --> CriarUC
    LoginUC --> PublicAPI
    LoginUC --> Ports
    CriarUC --> Usuario
    CriarUC --> Ports
    Jpa -. implementa .-> PublicAPI
    Jpa -. implementa .-> Ports
    BCrypt -. implementa .-> Ports
    JWT -. implementa .-> Ports
    Jpa --> DB
    Flyway --> DB
```

