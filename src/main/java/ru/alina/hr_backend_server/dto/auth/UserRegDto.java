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
@Schema(description = "DTO для регистрации пользователя")
public class UserRegDto {

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть валидным")
    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Min(value = 6, message = "Длина пароля должна быть не менее 6 символов")
    @Schema(description = "Пароль пользователя", example = "password")
    private String password;

    @NotBlank(message = "Имя не может быть пустым")
    @Schema(description = "Имя пользователя", example = "John")
    private String name;

    @Size(max = 50, message = "Длина фамилии не должна превышать 50 символов")
    @Schema(description = "Фамилия пользователя", example = "Doe")
    private String lastname;

    @Size(max = 50, message = "Длина отчества не должна превышать 50 символов")
    @Schema(description = "Отчество пользователя", example = "Smith")
    private String secondname;

    @NotNull(message = "Дата рождения не может быть пустой")
    @Past(message = "Дата рождения должна быть в прошлом")
    @Schema(description = "Дата рождения пользователя", example = "1990-01-01")
    private Date birthDay;
}