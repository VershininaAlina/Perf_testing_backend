package ru.alina.hr_backend_server.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для теста")
public class TestDto {

    @Schema(description = "Идентификатор теста", example = "1")
    private Long id;

    @Schema(description = "Название теста", example = "Тест по программированию")
    private String name;

    @Schema(description = "Описание теста", example = "Тестирование знаний программирования")
    private String description;

    @Schema(description = "Список вопросов")
    private List<QuestionDto> questionDtos;
}