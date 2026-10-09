package br.com.nexfleet.auth.login.application;

import br.com.nexfleet.usuarios.domain.Usuario;
import br.com.nexfleet.usuarios.publicapi.BuscarUsuarioPorEmail;
import br.com.nexfleet.usuarios.publicapi.UsuarioAutenticavel;

public final class LoginService implements LoginUseCase {

    private final BuscarUsuarioPorEmail usuarios;
    private final PasswordVerifier passwordVerifier;
    private final TokenIssuer tokenIssuer;

    public LoginService(
            BuscarUsuarioPorEmail usuarios,
            PasswordVerifier passwordVerifier,
            TokenIssuer tokenIssuer
    ) {
        this.usuarios = usuarios;
        this.passwordVerifier = passwordVerifier;
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    public LoginResult executar(LoginCommand command) {
        String email = Usuario.normalizarEmail(command.email());
        UsuarioAutenticavel usuario = usuarios.buscar(email)
                .filter(UsuarioAutenticavel::ativo)
                .orElseThrow(CredenciaisInvalidasException::new);

        if (command.senha() == null || !passwordVerifier.confere(command.senha(), usuario.senhaHash())) {
            throw new CredenciaisInvalidasException();
        }

        TokenEmitido token = tokenIssuer.emitir(usuario);
        return new LoginResult(
                token.valor(),
                token.expiresInSeconds(),
                usuario.id(),
                usuario.nome(),
                usuario.perfil()
        );
    }
}

