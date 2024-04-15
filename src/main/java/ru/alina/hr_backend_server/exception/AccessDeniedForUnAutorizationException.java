package ru.alina.hr_backend_server.exception;

import lombok.Data;

@Data
public class AccessDeniedForUnAutorizationException extends RuntimeException {
    private String message;

    public AccessDeniedForUnAutorizationException(String message) {
        this.message = message;
    }
}
