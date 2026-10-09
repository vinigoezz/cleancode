package br.com.nexfleet.auth.login.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.nexfleet.usuarios.domain.Perfil;
import br.com.nexfleet.usuarios.publicapi.BuscarUsuarioPorEmail;
import br.com.nexfleet.usuarios.publicapi.UsuarioAutenticavel;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LoginServiceTest {

    private static final UsuarioAutenticavel USUARIO = new UsuarioAutenticavel(
            UUID.fromString("b3a5a5ca-0fd1-48f8-b71c-fbd3fdb124ee"),
            "Administrador",
            "admin@nexfleet.local",
            "hash-correto",
            Perfil.ADMINISTRADOR,
            true
    );

    @Test
    void deveEmitirTokenParaCredenciaisValidas() {
        LoginService service = serviceCom(USUARIO, true);

        LoginResult result = service.executar(new LoginCommand("ADMIN@NEXFLEET.LOCAL", "senha"));

        assertThat(result.accessToken()).isEqualTo("jwt-assinado");
        assertThat(result.perfil()).isEqualTo(Perfil.ADMINISTRADOR);
        assertThat(result.expiresInSeconds()).isEqualTo(1800);
    }

    @Test
    void deveUsarMensagemGenericaQuandoSenhaFalha() {
        LoginService service = serviceCom(USUARIO, false);

        assertThatThrownBy(() -> service.executar(new LoginCommand("admin@nexfleet.local", "errada")))
                .isInstanceOf(CredenciaisInvalidasException.class)
                .hasMessage("E-mail ou senha inválidos");
    }

    @Test
    void deveRejeitarUsuarioInativo() {
        UsuarioAutenticavel inativo = new UsuarioAutenticavel(
                USUARIO.id(), USUARIO.nome(), USUARIO.email(), USUARIO.senhaHash(), USUARIO.perfil(), false
        );
        LoginService service = serviceCom(inativo, true);

        assertThatThrownBy(() -> service.executar(new LoginCommand("admin@nexfleet.local", "senha")))
                .isInstanceOf(CredenciaisInvalidasException.class);
    }

    private LoginService serviceCom(UsuarioAutenticavel usuario, boolean senhaConfere) {
        BuscarUsuarioPorEmail repository = email -> Optional.ofNullable(usuario);
        PasswordVerifier verifier = (senha, hash) -> senhaConfere;
        TokenIssuer issuer = autenticavel -> new TokenEmitido("jwt-assinado", 1800);
        return new LoginService(repository, verifier, issuer);
    }
}

