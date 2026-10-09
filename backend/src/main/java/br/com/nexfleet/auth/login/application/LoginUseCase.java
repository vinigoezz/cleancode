package br.com.nexfleet.auth.login.application;

public interface LoginUseCase {

    LoginResult executar(LoginCommand command);
}

