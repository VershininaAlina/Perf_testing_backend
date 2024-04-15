package ru.alina.hr_backend_server.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for User Registration")
public class UserRegistrationDto {

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password length must be between 6 and 20 characters")
    @Schema(description = "User password", example = "password123")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    @Schema(description = "User email address", example = "user@example.com")
    private String email;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name length must not exceed 50 characters")
    @Schema(description = "User's first name", example = "John")
    private String name;

    @Size(max = 50, message = "Last name length must not exceed 50 characters")
    @Schema(description = "User's last name", example = "Doe")
    private String lastname;

    @Size(max = 50, message = "Second name length must not exceed 50 characters")
    @Schema(description = "User's second name", example = "Smith")
    private String secondname;

    @NotNull(message = "Birth day is required")
    @Past(message = "Birth day must be a past date")
    @Schema(description = "User's date of birth", example = "1990-01-01")
    private Date birthDay;
}