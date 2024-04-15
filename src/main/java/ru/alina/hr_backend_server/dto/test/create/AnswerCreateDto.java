package ru.alina.hr_backend_server.dto.test.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания ответа")
public class AnswerCreateDto {

    @NotBlank(message = "Ответ не может быть пустым")
    @Schema(description = "Текст ответа", example = "Вариант ответа")
    private String answer;
}