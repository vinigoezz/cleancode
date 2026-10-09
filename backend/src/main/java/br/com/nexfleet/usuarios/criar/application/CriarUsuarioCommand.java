package br.com.nexfleet.usuarios.criar.application;

import br.com.nexfleet.usuarios.domain.Perfil;

public record CriarUsuarioCommand(
        String nome,
        String email,
        String senha,
        Perfil perfil
) {
}

