package ru.alina.hr_backend_server.dto.test.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания теста")
public class TestCreateDto {

    @NotBlank(message = "Название теста не может быть пустым")
    @Schema(description = "Название теста", example = "Тест по программированию")
    private String name;

    @NotBlank(message = "Описание теста не может быть пустым")
    @Size(max = 500, message = "Длина описания теста не должна превышать 500 символов")
    @Schema(description = "Описание теста", example = "Тестирование знаний программирования")
    private String description;

    @Valid
    @NotNull(message = "Список вопросов не может быть пустым")
    @Schema(description = "Список вопросов")
    private List<QuestionCreateDto> questionDtos;
}