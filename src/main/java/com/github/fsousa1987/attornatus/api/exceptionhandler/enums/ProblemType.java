package com.github.fsousa1987.attornatus.api.exceptionhandler.enums;

import lombok.Getter;

@Getter
public enum ProblemType {

    RESOURCE_NOT_FOUND("Resource not found"),
    INVALID_PRINCIPAL_ADDRESS("Invalid principal address provided");

    private final String title;

    ProblemType(String title) {
        this.title = title;
    }
}
