package ru.alina.hr_backend_server.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для ответа")
public class AnswerDto {

    @Schema(description = "Идентификатор ответа", example = "1")
    private Long id;

    @Schema(description = "Текст ответа", example = "Вариант ответа")
    private String answer;
}