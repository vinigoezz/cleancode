package br.com.nexfleet.usuarios.infrastructure.security;

import br.com.nexfleet.usuarios.criar.application.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String proteger(String senha) {
        return passwordEncoder.encode(senha);
    }
}

