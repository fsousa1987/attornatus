package com.github.fsousa1987.attornatus.api.exceptionhandler;

import com.github.fsousa1987.attornatus.api.exceptionhandler.enums.ProblemType;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.InvalidEnderecoPrincipalException;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.PessoaNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<?> handlePessoaNaoEncontradaException(PessoaNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(InvalidEnderecoPrincipalException.class)
    public ResponseEntity<?> handleInvalidEnderecoPrincipalException(InvalidEnderecoPrincipalException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.INVALID_PRINCIPAL_ADDRESS;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail) {
        return Problem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .title(problemType.getTitle())
                .detail(detail);
    }

}
