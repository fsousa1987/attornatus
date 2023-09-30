package com.github.fsousa1987.attornatus.api.exceptionhandler.enums;

import lombok.Getter;

@Getter
public enum ProblemType {

    RESOURCE_NOT_FOUND("Recurso não encontrado"),
    INVALID_PRINCIPAL_ADDRESS("Endereço principal inválido"),
    INVALID_DATA("Dados inválidos"),
    INCOMPREHENSIBLE_MESSAGE("Incomprehensible message"),
    INVALID_PARAMETER("Invalid parameter");

    private final String title;

    ProblemType(String title) {
        this.title = title;
    }
}
