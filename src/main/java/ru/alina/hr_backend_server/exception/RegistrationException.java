package ru.alina.hr_backend_server.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationException extends RuntimeException{
    private String message;

}
