package com.github.fsousa1987.attornatus.api.exceptionhandler.exceptions;

import java.io.Serial;

public class InvalidEnderecoPrincipalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidEnderecoPrincipalException(String message) {
        super(message);
    }
}
