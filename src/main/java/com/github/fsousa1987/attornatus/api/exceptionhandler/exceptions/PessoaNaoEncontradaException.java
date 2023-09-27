package com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions;

import java.io.Serial;

public class PessoaNaoEncontradaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PessoaNaoEncontradaException(String message) {
        super(message);
    }
}
