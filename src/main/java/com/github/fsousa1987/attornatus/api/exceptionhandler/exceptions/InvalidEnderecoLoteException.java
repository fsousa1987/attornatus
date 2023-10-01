package com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions;

import java.io.Serial;

public class InvalidEnderecoLoteException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidEnderecoLoteException(String message) {
        super(message);
    }
}
