package br.com.nexfleet.auth.infrastructure.security;

import br.com.nexfleet.auth.login.application.PasswordVerifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordVerifier implements PasswordVerifier {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordVerifier(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean confere(String senha, String senhaHash) {
        return passwordEncoder.matches(senha, senhaHash);
    }
}

