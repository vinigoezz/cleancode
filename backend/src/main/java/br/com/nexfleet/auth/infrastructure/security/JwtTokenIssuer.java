package br.com.nexfleet.auth.infrastructure.security;

import br.com.nexfleet.auth.login.application.TokenEmitido;
import br.com.nexfleet.auth.login.application.TokenIssuer;
import br.com.nexfleet.usuarios.publicapi.UsuarioAutenticavel;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

public final class JwtTokenIssuer implements TokenIssuer {

    private final JwtEncoder jwtEncoder;
    private final Clock clock;
    private final Duration validade;

    public JwtTokenIssuer(JwtEncoder jwtEncoder, Clock clock, Duration validade) {
        this.jwtEncoder = jwtEncoder;
        this.clock = clock;
        this.validade = validade;
    }

    @Override
    public TokenEmitido emitir(UsuarioAutenticavel usuario) {
        Instant agora = clock.instant();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("nexfleet-api")
                .issuedAt(agora)
                .expiresAt(agora.plus(validade))
                .subject(usuario.id().toString())
                .claim("email", usuario.email())
                .claim("nome", usuario.nome())
                .claim("perfil", usuario.perfil().name())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String valor = jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
        return new TokenEmitido(valor, validade.toSeconds());
    }
}

