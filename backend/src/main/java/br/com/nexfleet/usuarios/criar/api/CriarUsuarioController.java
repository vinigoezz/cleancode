package br.com.nexfleet.usuarios.criar.api;

import br.com.nexfleet.usuarios.criar.application.CriarUsuarioCommand;
import br.com.nexfleet.usuarios.criar.application.CriarUsuarioResult;
import br.com.nexfleet.usuarios.criar.application.CriarUsuarioUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
public class CriarUsuarioController {

    private final CriarUsuarioUseCase criarUsuario;

    public CriarUsuarioController(CriarUsuarioUseCase criarUsuario) {
        this.criarUsuario = criarUsuario;
    }

    @PostMapping
    public ResponseEntity<CriarUsuarioResponse> criar(@Valid @RequestBody CriarUsuarioRequest request) {
        CriarUsuarioResult result = criarUsuario.executar(new CriarUsuarioCommand(
                request.nome(), request.email(), request.senha(), request.perfil()
        ));
        CriarUsuarioResponse response = new CriarUsuarioResponse(
                result.id(), result.nome(), result.email(), result.perfil()
        );
        return ResponseEntity.created(URI.create("/api/v1/usuarios/" + result.id())).body(response);
    }
}

