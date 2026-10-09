package br.com.nexfleet.usuarios.infrastructure.bootstrap;

import br.com.nexfleet.usuarios.criar.application.CriarUsuarioCommand;
import br.com.nexfleet.usuarios.criar.application.CriarUsuarioUseCase;
import br.com.nexfleet.usuarios.criar.application.EmailJaCadastradoException;
import br.com.nexfleet.usuarios.domain.Perfil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBootstrap.class);

    private final CriarUsuarioUseCase criarUsuario;
    private final String nome;
    private final String email;
    private final String senha;

    public AdminBootstrap(
            CriarUsuarioUseCase criarUsuario,
            @Value("${nexfleet.bootstrap.admin-name}") String nome,
            @Value("${nexfleet.bootstrap.admin-email}") String email,
            @Value("${nexfleet.bootstrap.admin-password}") String senha
    ) {
        this.criarUsuario = criarUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (senha == null || senha.isBlank()) {
            LOGGER.warn("Administrador inicial não criado: NEXFLEET_ADMIN_PASSWORD não foi definido");
            return;
        }
        try {
            criarUsuario.executar(new CriarUsuarioCommand(nome, email, senha, Perfil.ADMINISTRADOR));
            LOGGER.info("Administrador inicial criado para o e-mail configurado");
        } catch (EmailJaCadastradoException ignored) {
            LOGGER.info("Administrador inicial já existe");
        }
    }
}

