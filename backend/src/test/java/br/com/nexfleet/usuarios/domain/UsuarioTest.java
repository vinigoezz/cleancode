package br.com.nexfleet.usuarios.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void deveNormalizarEmailAoCriarUsuario() {
        Usuario usuario = Usuario.criar(
                "Lucas Roussenq",
                "  LUCAS@EXEMPLO.COM  ",
                "hash",
                Perfil.ADMINISTRADOR,
                Instant.parse("2026-10-08T12:00:00Z")
        );

        assertThat(usuario.email()).isEqualTo("lucas@exemplo.com");
        assertThat(usuario.ativo()).isTrue();
    }

    @Test
    void deveRejeitarEmailInvalido() {
        assertThatThrownBy(() -> Usuario.criar(
                "Usuário",
                "email-invalido",
                "hash",
                Perfil.GESTOR_FROTA,
                Instant.now()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O e-mail informado é inválido");
    }
}

