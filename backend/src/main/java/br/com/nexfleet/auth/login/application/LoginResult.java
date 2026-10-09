package br.com.nexfleet.auth.login.application;

import br.com.nexfleet.usuarios.domain.Perfil;
import java.util.UUID;

public record LoginResult(
        String accessToken,
        long expiresInSeconds,
        UUID usuarioId,
        String nome,
        Perfil perfil
) {
}

