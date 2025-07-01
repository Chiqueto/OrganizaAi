package com.organizaAi.OrganizaAi.infra.exceptions;

public class AlreadyExistsException extends RuntimeException {
    private final String field;
    public AlreadyExistsException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}