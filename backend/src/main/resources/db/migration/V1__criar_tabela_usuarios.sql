CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(254) NOT NULL,
    senha_hash VARCHAR(100) NOT NULL,
    perfil VARCHAR(30) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMPTZ NOT NULL,
    CONSTRAINT ck_usuarios_perfil
        CHECK (perfil IN ('ADMINISTRADOR', 'GESTOR_FROTA'))
);

CREATE UNIQUE INDEX uk_usuarios_email_normalizado
    ON usuarios (LOWER(email));

