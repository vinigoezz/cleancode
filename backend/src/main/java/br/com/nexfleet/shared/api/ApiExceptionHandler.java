package br.com.nexfleet.shared.api;

import br.com.nexfleet.auth.login.application.CredenciaisInvalidasException;
import br.com.nexfleet.usuarios.criar.application.EmailJaCadastradoException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CredenciaisInvalidasException.class)
    ResponseEntity<ProblemDetail> credenciaisInvalidas(CredenciaisInvalidasException exception) {
        return problem(HttpStatus.UNAUTHORIZED, "Credenciais inválidas", exception.getMessage());
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    ResponseEntity<ProblemDetail> emailDuplicado(EmailJaCadastradoException exception) {
        return problem(HttpStatus.CONFLICT, "Conflito de cadastro", exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ProblemDetail> regraInvalida(IllegalArgumentException exception) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, "Regra de negócio inválida", exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ProblemDetail> validacao(MethodArgumentNotValidException exception) {
        ProblemDetail detail = base(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Dados de entrada inválidos",
                "Revise os campos informados"
        );
        Map<String, String> fields = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                fields.putIfAbsent(error.getField(), error.getDefaultMessage()));
        detail.setProperty("fields", fields);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(detail);
    }

    private ResponseEntity<ProblemDetail> problem(HttpStatus status, String title, String message) {
        return ResponseEntity.status(status).body(base(status, title, message));
    }

    private ProblemDetail base(HttpStatus status, String title, String message) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, message);
        detail.setTitle(title);
        detail.setType(URI.create("https://nexfleet.local/problems/" + status.value()));
        return detail;
    }
}

