package br.com.nexfleet.usuarios.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataUsuarioRepository extends JpaRepository<UsuarioJpaEntity, UUID> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<UsuarioJpaEntity> findByEmailIgnoreCase(String email);
}

