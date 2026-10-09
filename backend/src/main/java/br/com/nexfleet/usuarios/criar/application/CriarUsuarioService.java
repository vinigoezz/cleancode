package br.com.nexfleet.usuarios.criar.application;

import br.com.nexfleet.usuarios.domain.Usuario;
import java.time.Clock;

public final class CriarUsuarioService implements CriarUsuarioUseCase {

    private final UsuarioRepository usuarios;
    private final PasswordHasher passwordHasher;
    private final Clock clock;

    public CriarUsuarioService(UsuarioRepository usuarios, PasswordHasher passwordHasher, Clock clock) {
        this.usuarios = usuarios;
        this.passwordHasher = passwordHasher;
        this.clock = clock;
    }

    @Override
    public CriarUsuarioResult executar(CriarUsuarioCommand command) {
        validarSenha(command.senha());
        String email = Usuario.normalizarEmail(command.email());
        if (usuarios.existePorEmail(email)) {
            throw new EmailJaCadastradoException();
        }

        Usuario usuario = Usuario.criar(
                command.nome(),
                email,
                passwordHasher.proteger(command.senha()),
                command.perfil(),
                clock.instant()
        );
        Usuario salvo = usuarios.salvar(usuario);
        return new CriarUsuarioResult(salvo.id(), salvo.nome(), salvo.email(), salvo.perfil());
    }

    private static void validarSenha(String senha) {
        if (senha == null || senha.length() < 8 || senha.length() > 72) {
            throw new IllegalArgumentException("A senha deve ter entre 8 e 72 caracteres");
        }
    }
}

