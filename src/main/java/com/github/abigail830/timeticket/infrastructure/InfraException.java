package com.github.abigail830.timeticket.infrastructure;

public class InfraException extends RuntimeException {

    public InfraException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfraException(String message) {
        super(message);
    }
}
