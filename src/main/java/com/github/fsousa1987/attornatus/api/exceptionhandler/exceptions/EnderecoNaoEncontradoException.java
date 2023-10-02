package com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions;

import java.io.Serial;

public class EnderecoNaoEncontradoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EnderecoNaoEncontradoException(String message) {
        super(message);
    }
}
