package ru.alina.hr_backend_server.dto.vacancy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.dto.test.TestDto;
import ru.alina.hr_backend_server.dto.user.UserDto;
import ru.alina.hr_backend_server.dto.user_test_passed.UserTestPassedDto;
import ru.alina.hr_backend_server.dto.user_test_passed.UserTestPassedDtos;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для вакансии (администратор)")
public class VacancyAdminDto {

    @Schema(description = "Идентификатор вакансии", example = "1")
    private Long id;

    @Schema(description = "Название вакансии", example = "Backend Developer")
    private String name;

    @Schema(description = "Описание вакансии", example = "Мы ищем опытного Backend разработчика")
    private String description;

    @Schema(description = "Обязанности", example = "Разработка и поддержка Backend-части проектов")
    private String responsibilities;

    @Schema(description = "Требования", example = "Опыт работы с Java, знание SQL")
    private String requirements;

    @Schema(description = "Что будет плюсом", example = "Опыт работы с Spring Framework")
    private String willPlus;

    @Schema(description = "Условия работы", example = "Гибкий график, удаленная работа")
    private String condition;

    @Schema(description = "Список тестов связанных с вакансией")
    private List<TestDto> testDtos;

    @Schema(description = "Список пользователей, откликнувшихся на вакансию")
    private List<UserDto> responseVacancyUser;

    private Integer numberOfResponded;
    private Integer numberOfPassedTests;

    private List<UserTestPassedDtos> userTestPassedDtos;

}