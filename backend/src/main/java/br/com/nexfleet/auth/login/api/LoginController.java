package br.com.nexfleet.auth.login.api;

import br.com.nexfleet.auth.login.application.LoginCommand;
import br.com.nexfleet.auth.login.application.LoginResult;
import br.com.nexfleet.auth.login.application.LoginUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    private final LoginUseCase login;

    public LoginController(LoginUseCase login) {
        this.login = login;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = login.executar(new LoginCommand(request.email(), request.senha()));
        return ResponseEntity.ok(new LoginResponse(
                result.accessToken(),
                "Bearer",
                result.expiresInSeconds(),
                result.usuarioId(),
                result.nome(),
                result.perfil()
        ));
    }
}

