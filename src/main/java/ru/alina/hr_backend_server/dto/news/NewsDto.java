package ru.alina.hr_backend_server.dto.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для новости")
public class NewsDto {

    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(max = 100, message = "Заголовок должен быть не более 100 символов")
    @Schema(description = "Заголовок новости", example = "Новость")
    private String header;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(max = 500, message = "Описание должно быть не более 500 символов")
    @Schema(description = "Описание новости", example = "Краткое описание новости")
    private String description;

    @NotBlank(message = "Текст новости не может быть пустым")
    @Schema(description = "Текст новости", example = "Полный текст новости")
    private String body;
}