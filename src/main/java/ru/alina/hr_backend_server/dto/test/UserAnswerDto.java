package ru.alina.hr_backend_server.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для ответа пользователя")
public class UserAnswerDto {

    @NotNull(message = "Идентификатор вопроса не может быть пустым")
    @Schema(description = "Идентификатор вопроса", example = "1")
    private Long questionId;

    @NotNull(message = "Список идентификаторов ответов не может быть пустым")
    @Schema(description = "Список идентификаторов ответов")
    private List<Long> answersId;
}