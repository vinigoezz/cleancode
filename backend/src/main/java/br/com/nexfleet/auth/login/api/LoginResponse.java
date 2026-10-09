package br.com.nexfleet.auth.login.api;

import br.com.nexfleet.usuarios.domain.Perfil;
import java.util.UUID;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UUID usuarioId,
        String nome,
        Perfil perfil
) {
}

