package br.com.nexfleet.usuarios.criar.application;

import br.com.nexfleet.usuarios.domain.Usuario;

public interface UsuarioRepository {

    boolean existePorEmail(String email);

    Usuario salvar(Usuario usuario);
}

