package br.com.nexfleet.usuarios.publicapi;

import java.util.Optional;

public interface BuscarUsuarioPorEmail {

    Optional<UsuarioAutenticavel> buscar(String email);
}

