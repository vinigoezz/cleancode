package br.com.nexfleet.usuarios.criar.application;

import br.com.nexfleet.usuarios.domain.Perfil;
import java.util.UUID;

public record CriarUsuarioResult(
        UUID id,
        String nome,
        String email,
        Perfil perfil
) {
}

