package ru.alina.hr_backend_server.dto.vacancy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для создания вакансии")
public class VacancyCreateDto {

    @NotBlank(message = "Название вакансии не может быть пустым")
    @Size(max = 100, message = "Название вакансии не может превышать {max} символов")
    @Schema(description = "Название вакансии", example = "Backend Developer")
    private String name;

    @NotBlank(message = "Описание вакансии не может быть пустым")
    @Size(max = 4096, message = "Описание вакансии не может превышать {max} символов")
    @Schema(description = "Описание вакансии", example = "Мы ищем опытного Backend разработчика")
    private String description;

    @Size(max = 500, message = "Обязанности не могут превышать {max} символов")
    @Schema(description = "Обязанности", example = "Разработка и поддержка Backend-части проектов")
    private String responsibilities;

    @Size(max = 500, message = "Требования не могут превышать {max} символов")
    @Schema(description = "Требования", example = "Опыт работы с Java, знание SQL")
    private String requirements;

    @Size(max = 500, message = "Что будет плюсом не может превышать {max} символов")
    @Schema(description = "Что будет плюсом", example = "Опыт работы с Spring Framework")
    private String willPlus;

    @Size(max = 500, message = "Условия работы не могут превышать {max} символов")
    @Schema(description = "Условия работы", example = "Гибкий график, удаленная работа")
    private String conditions;
}
