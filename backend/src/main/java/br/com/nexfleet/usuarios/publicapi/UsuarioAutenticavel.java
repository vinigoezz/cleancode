package br.com.nexfleet.usuarios.publicapi;

import br.com.nexfleet.usuarios.domain.Perfil;
import java.util.UUID;

public record UsuarioAutenticavel(
        UUID id,
        String nome,
        String email,
        String senhaHash,
        Perfil perfil,
        boolean ativo
) {
}

