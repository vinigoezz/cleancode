package br.com.nexfleet.usuarios.criar.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.nexfleet.usuarios.domain.Perfil;
import br.com.nexfleet.usuarios.domain.Usuario;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CriarUsuarioServiceTest {

    private static final Instant AGORA = Instant.parse("2026-10-08T12:00:00Z");

    @Test
    void deveProtegerSenhaEPersistirUsuario() {
        InMemoryUsuarioRepository repository = new InMemoryUsuarioRepository();
        CriarUsuarioService service = new CriarUsuarioService(
                repository,
                senha -> "bcrypt:" + senha,
                Clock.fixed(AGORA, ZoneOffset.UTC)
        );

        CriarUsuarioResult result = service.executar(new CriarUsuarioCommand(
                "Vinícius Reis",
                "VINICIUS@EXEMPLO.COM",
                "senha-segura",
                Perfil.ADMINISTRADOR
        ));

        assertThat(result.email()).isEqualTo("vinicius@exemplo.com");
        assertThat(repository.salvos).singleElement()
                .extracting(Usuario::senhaHash)
                .isEqualTo("bcrypt:senha-segura");
    }

    @Test
    void deveRejeitarEmailJaCadastrado() {
        InMemoryUsuarioRepository repository = new InMemoryUsuarioRepository();
        repository.emailExistente = true;
        CriarUsuarioService service = new CriarUsuarioService(
                repository,
                senha -> "hash",
                Clock.fixed(AGORA, ZoneOffset.UTC)
        );

        assertThatThrownBy(() -> service.executar(new CriarUsuarioCommand(
                "Erik Carvalho",
                "erik@exemplo.com",
                "senha-segura",
                Perfil.GESTOR_FROTA
        )))
                .isInstanceOf(EmailJaCadastradoException.class);
    }

    @Test
    void deveRejeitarSenhaCurta() {
        CriarUsuarioService service = new CriarUsuarioService(
                new InMemoryUsuarioRepository(),
                senha -> "hash",
                Clock.fixed(AGORA, ZoneOffset.UTC)
        );

        assertThatThrownBy(() -> service.executar(new CriarUsuarioCommand(
                "Mateus Burlamaqui",
                "mateus@exemplo.com",
                "curta",
                Perfil.GESTOR_FROTA
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("A senha deve ter entre 8 e 72 caracteres");
    }

    private static final class InMemoryUsuarioRepository implements UsuarioRepository {
        private final List<Usuario> salvos = new ArrayList<>();
        private boolean emailExistente;

        @Override
        public boolean existePorEmail(String email) {
            return emailExistente;
        }

        @Override
        public Usuario salvar(Usuario usuario) {
            salvos.add(usuario);
            return usuario;
        }
    }
}

