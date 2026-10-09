package br.com.nexfleet.auth.login.application;

import br.com.nexfleet.usuarios.publicapi.UsuarioAutenticavel;

public interface TokenIssuer {

    TokenEmitido emitir(UsuarioAutenticavel usuario);
}

