package ru.alina.hr_backend_server.dto.experience;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для опыта работы")
public class ExperienceDto {

    @NotBlank(message = "Должность не может быть пустой")
    @Schema(description = "Должность", example = "Software Engineer")
    private String position;

    @NotNull(message = "Год опыта не может быть пустым")
    @Positive(message = "Год опыта должен быть положительным числом")
    @Schema(description = "Год опыта", example = "5")
    private Double yearOfExperience;

    @Size(max = 500, message = "Описание должно иметь длину не более 500 символов")
    @Schema(description = "Описание опыта работы", example = "I worked on developing web applications using Java and Spring Framework.")
    private String description;
}