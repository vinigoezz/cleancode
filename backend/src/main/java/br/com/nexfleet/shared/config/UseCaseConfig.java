package br.com.nexfleet.shared.config;

import br.com.nexfleet.auth.infrastructure.security.JwtTokenIssuer;
import br.com.nexfleet.auth.login.application.LoginService;
import br.com.nexfleet.auth.login.application.LoginUseCase;
import br.com.nexfleet.auth.login.application.PasswordVerifier;
import br.com.nexfleet.auth.login.application.TokenIssuer;
import br.com.nexfleet.usuarios.criar.application.CriarUsuarioService;
import br.com.nexfleet.usuarios.criar.application.CriarUsuarioUseCase;
import br.com.nexfleet.usuarios.criar.application.PasswordHasher;
import br.com.nexfleet.usuarios.criar.application.UsuarioRepository;
import br.com.nexfleet.usuarios.publicapi.BuscarUsuarioPorEmail;
import java.time.Clock;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@Configuration
public class UseCaseConfig {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    CriarUsuarioUseCase criarUsuarioUseCase(
            UsuarioRepository usuarios,
            PasswordHasher passwordHasher,
            Clock clock
    ) {
        return new CriarUsuarioService(usuarios, passwordHasher, clock);
    }

    @Bean
    TokenIssuer tokenIssuer(
            JwtEncoder jwtEncoder,
            Clock clock,
            @Value("${nexfleet.security.jwt-expiration-minutes}") long expirationMinutes
    ) {
        return new JwtTokenIssuer(jwtEncoder, clock, Duration.ofMinutes(expirationMinutes));
    }

    @Bean
    LoginUseCase loginUseCase(
            BuscarUsuarioPorEmail usuarios,
            PasswordVerifier passwordVerifier,
            TokenIssuer tokenIssuer
    ) {
        return new LoginService(usuarios, passwordVerifier, tokenIssuer);
    }
}

