package ru.alina.hr_backend_server.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.entity.question.AnswerType;
import ru.alina.hr_backend_server.entity.question.QuestionType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для вопроса")
public class QuestionDto {

    @Schema(description = "Идентификатор вопроса", example = "1")
    private Long id;

    @Schema(description = "Текст вопроса", example = "Вопрос")
    private String question;

    @Schema(description = "Тип вопроса (Одиночный выбор, Множественный выбор, Открытый ответ)")
    private QuestionType questionType;

    @Schema(description = "Тип ответа (Текст, Число, Дата)")
    private AnswerType answerType;

    @Schema(description = "Список ответов")
    private List<AnswerDto> answerDtoList;

    @Schema(description = "Список правильных ответов")
    private List<AnswerDto> correctAnswerDtoList;
}