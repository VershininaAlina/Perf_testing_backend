package ru.alina.hr_backend_server.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.image.Image;
import ru.alina.hr_backend_server.entity.profile.Experience;
import ru.alina.hr_backend_server.entity.user.Role;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для пользователя")
public class UserDto {

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    @Schema(description = "Имя пользователя", example = "John")
    private String name;

    @Schema(description = "Фамилия пользователя", example = "Doe")
    private String lastname;

    @Schema(description = "Отчество пользователя", example = "Smith")
    private String secondname;

    @Schema(description = "Фото профиля пользователя")
    private Image photoProfile;

    @Schema(description = "Дата рождения пользователя", example = "1990-01-01")
    private Date birthDay;

    @Schema(description = "Список опыта работы пользователя")
    private List<Experience> expiriences;

    private Role role;
    
}