package br.com.nexfleet.usuarios.domain;

import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public final class Usuario {

    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final UUID id;
    private final String nome;
    private final String email;
    private final String senhaHash;
    private final Perfil perfil;
    private final boolean ativo;
    private final Instant criadoEm;

    private Usuario(
            UUID id,
            String nome,
            String email,
            String senhaHash,
            Perfil perfil,
            boolean ativo,
            Instant criadoEm
    ) {
        this.id = Objects.requireNonNull(id);
        this.nome = validarNome(nome);
        this.email = normalizarEmail(email);
        this.senhaHash = exigirTexto(senhaHash, "A senha protegida é obrigatória");
        this.perfil = Objects.requireNonNull(perfil, "O perfil é obrigatório");
        this.ativo = ativo;
        this.criadoEm = Objects.requireNonNull(criadoEm);
    }

    public static Usuario criar(String nome, String email, String senhaHash, Perfil perfil, Instant agora) {
        return new Usuario(UUID.randomUUID(), nome, email, senhaHash, perfil, true, agora);
    }

    public static Usuario reconstituir(
            UUID id,
            String nome,
            String email,
            String senhaHash,
            Perfil perfil,
            boolean ativo,
            Instant criadoEm
    ) {
        return new Usuario(id, nome, email, senhaHash, perfil, ativo, criadoEm);
    }

    private static String validarNome(String nome) {
        String valor = exigirTexto(nome, "O nome é obrigatório").trim();
        if (valor.length() > 120) {
            throw new IllegalArgumentException("O nome deve ter no máximo 120 caracteres");
        }
        return valor;
    }

    public static String normalizarEmail(String email) {
        String valor = exigirTexto(email, "O e-mail é obrigatório").trim().toLowerCase(Locale.ROOT);
        if (valor.length() > 254 || !EMAIL.matcher(valor).matches()) {
            throw new IllegalArgumentException("O e-mail informado é inválido");
        }
        return valor;
    }

    private static String exigirTexto(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
        return valor;
    }

    public UUID id() {
        return id;
    }

    public String nome() {
        return nome;
    }

    public String email() {
        return email;
    }

    public String senhaHash() {
        return senhaHash;
    }

    public Perfil perfil() {
        return perfil;
    }

    public boolean ativo() {
        return ativo;
    }

    public Instant criadoEm() {
        return criadoEm;
    }
}

