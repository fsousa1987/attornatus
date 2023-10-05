package com.github.fsousa1987.attornatus.api.exceptionhandler.enums;

import lombok.Getter;

@Getter
public enum ProblemType {

    RESOURCE_NOT_FOUND("Recurso não encontrado"),
    INVALID_PRINCIPAL_ADDRESS("Endereço principal inválido"),
    INVALID_LOTE_ADDRESS("Lote de endereços inválido"),
    INVALID_DATA("Dados inválidos"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível");

    private final String title;

    ProblemType(String title) {
        this.title = title;
    }
}
