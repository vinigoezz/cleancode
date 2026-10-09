package br.com.nexfleet.auth.login.application;

public record TokenEmitido(String valor, long expiresInSeconds) {
}

