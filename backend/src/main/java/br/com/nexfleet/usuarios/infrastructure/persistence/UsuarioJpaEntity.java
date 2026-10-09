package br.com.nexfleet.usuarios.infrastructure.persistence;

import br.com.nexfleet.usuarios.domain.Perfil;
import br.com.nexfleet.usuarios.domain.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
class UsuarioJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 254)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 100)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Perfil perfil;

    @Column(nullable = false)
    private boolean ativo;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    protected UsuarioJpaEntity() {
    }

    private UsuarioJpaEntity(Usuario usuario) {
        this.id = usuario.id();
        this.nome = usuario.nome();
        this.email = usuario.email();
        this.senhaHash = usuario.senhaHash();
        this.perfil = usuario.perfil();
        this.ativo = usuario.ativo();
        this.criadoEm = usuario.criadoEm();
    }

    static UsuarioJpaEntity fromDomain(Usuario usuario) {
        return new UsuarioJpaEntity(usuario);
    }

    Usuario toDomain() {
        return Usuario.reconstituir(id, nome, email, senhaHash, perfil, ativo, criadoEm);
    }
}

