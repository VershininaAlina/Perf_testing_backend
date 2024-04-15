package ru.alina.hr_backend_server.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для редактирования профиля")
public class EditProfileDto {

    @NotNull(message = "Имя не может быть пустым")
    @Size(max = 50, message = "Имя должно быть не более 50 символов")
    @Schema(description = "Имя пользователя", example = "John")
    private String name;

    @Size(max = 50, message = "Фамилия должна быть не более 50 символов")
    @Schema(description = "Фамилия пользователя", example = "Doe")
    private String lastname;

    @Size(max = 50, message = "Отчество должно быть не более 50 символов")
    @Schema(description = "Отчество пользователя", example = "Smith")
    private String secondname;

    @NotNull(message = "Дата рождения не может быть пустой")
    @Schema(description = "Дата рождения пользователя", example = "1990-01-01")
    private Date birthDay;
}