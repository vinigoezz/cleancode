package br.com.nexfleet.auth.login.application;

public interface PasswordVerifier {

    boolean confere(String senha, String senhaHash);
}

