package br.com.nexfleet.usuarios.criar.api;

import br.com.nexfleet.usuarios.domain.Perfil;
import java.util.UUID;

public record CriarUsuarioResponse(
        UUID id,
        String nome,
        String email,
        Perfil perfil
) {
}

