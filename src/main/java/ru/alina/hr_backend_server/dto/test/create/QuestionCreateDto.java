package ru.alina.hr_backend_server.dto.test.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.question.AnswerType;
import ru.alina.hr_backend_server.entity.question.QuestionType;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания вопроса")
public class QuestionCreateDto {

    @NotBlank(message = "Вопрос не может быть пустым")
    @Schema(description = "Текст вопроса", example = "Вопрос")
    private String question;

    @NotNull(message = "Тип вопроса не может быть пустым")
    @Schema(description = "Тип вопроса (Одиночный выбор, Множественный выбор, Открытый ответ)")
    private QuestionType questionType;

    @NotNull(message = "Тип ответа не может быть пустым")
    @Schema(description = "Тип ответа (Текст, Число, Дата)")
    private AnswerType answerType;

    @Valid
    @NotNull(message = "Список ответов не может быть пустым")
    @Schema(description = "Список ответов")
    private List<AnswerCreateDto> answer;

    @Valid
    @Schema(description = "Список вариантов (только для вопросов с одиночным или множественным выбором)")
    private List<AnswerCreateDto> options;
}