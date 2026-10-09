package br.com.nexfleet.usuarios.criar.application;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException() {
        super("Já existe um usuário cadastrado com este e-mail");
    }
}

