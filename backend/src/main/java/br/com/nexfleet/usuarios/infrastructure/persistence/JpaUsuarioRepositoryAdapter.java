package br.com.nexfleet.usuarios.infrastructure.persistence;

import br.com.nexfleet.usuarios.criar.application.UsuarioRepository;
import br.com.nexfleet.usuarios.domain.Usuario;
import br.com.nexfleet.usuarios.publicapi.BuscarUsuarioPorEmail;
import br.com.nexfleet.usuarios.publicapi.UsuarioAutenticavel;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUsuarioRepositoryAdapter implements UsuarioRepository, BuscarUsuarioPorEmail {

    private final SpringDataUsuarioRepository repository;

    public JpaUsuarioRepositoryAdapter(SpringDataUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return repository.save(UsuarioJpaEntity.fromDomain(usuario)).toDomain();
    }

    @Override
    public Optional<UsuarioAutenticavel> buscar(String email) {
        return repository.findByEmailIgnoreCase(email)
                .map(UsuarioJpaEntity::toDomain)
                .map(usuario -> new UsuarioAutenticavel(
                        usuario.id(),
                        usuario.nome(),
                        usuario.email(),
                        usuario.senhaHash(),
                        usuario.perfil(),
                        usuario.ativo()
                ));
    }
}

