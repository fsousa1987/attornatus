package com.github.fsousa1987.attornatus.api.exceptionhandler;

import com.github.fsousa1987.attornatus.api.exceptionhandler.enums.ProblemType;
import com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        var problemType = ProblemType.INVALID_DATA;
        var detail = "O campo data de nascimento está inválido. Preencha corretamente e tente outra vez";
        var problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<?> handlePessoaNaoEncontradaException(PessoaNaoEncontradaException ex, WebRequest request) {

        var status = HttpStatus.NOT_FOUND;
        var problemType = ProblemType.RESOURCE_NOT_FOUND;
        var detail = ex.getMessage();

        var problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(SemEnderecoPrincipalException.class)
    public ResponseEntity<?> handleInvalidEnderecoPrincipalException(
            SemEnderecoPrincipalException ex, WebRequest request) {

        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.INVALID_PRINCIPAL_ADDRESS;
        var detail = ex.getMessage();

        var problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(InvalidEnderecoLoteException.class)
    public ResponseEntity<?> handleInvalidEnderecoLoteException(InvalidEnderecoLoteException ex, WebRequest request) {

        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.INVALID_LOTE_ADDRESS;
        var detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EnderecoJaCadastradoException.class)
    public ResponseEntity<?> handleEnderecoJaCadastradoException(EnderecoJaCadastradoException ex, WebRequest request) {

        var status = HttpStatus.BAD_REQUEST;
        var problemType = ProblemType.INVALID_ADDRESS_PROVIDED;
        var detail = ex.getMessage();

        var problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    public ResponseEntity<?> handleEnderecoNaoEncontradoException(
            EnderecoNaoEncontradoException ex, WebRequest request) {

        var status = HttpStatus.NOT_FOUND;
        var problemType = ProblemType.RESOURCE_NOT_FOUND;
        var detail = ex.getMessage();

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

    private ResponseEntity<Object> handleValidationInternal(
            Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var problemType = ProblemType.INVALID_DATA;
        var detail = "Um ou mais campos estão inválidos. Preencha corretamente e tente outra vez";

        var problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    var message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    var name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        var problem = createProblemBuilder(status, problemType, detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

}
