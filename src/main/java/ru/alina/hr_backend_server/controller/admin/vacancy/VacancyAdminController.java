package ru.alina.hr_backend_server.controller.admin.vacancy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alina.hr_backend_server.dto.vacancy.VacancyAdminDto;
import ru.alina.hr_backend_server.dto.vacancy.VacancyCreateDto;
import ru.alina.hr_backend_server.dto.vacancy.VacancyDto;

import java.util.List;


@Validated
@RestController
@RequestMapping("/admin/vacancies")
@RequiredArgsConstructor
public class VacancyAdminController {
    private final VacancyAdminService vacancyAdminService;


    /**
     * Создание вакансии
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание вакансии", security = {@SecurityRequirement(name = "JWT")}

    )
    public VacancyDto createVacancy(@Valid @RequestBody VacancyCreateDto vacancyCreateDto) {
        return vacancyAdminService.createVacancy(vacancyCreateDto);
    }

    /**
     * Редактирование вакансии по ID
     */
    @PutMapping("/{id}")
    @Operation(description = "Редактирование вакансии по ID", security = {@SecurityRequirement(name = "JWT")})
    public VacancyDto editVacancy(@Valid @RequestBody VacancyCreateDto vacancyCreateDto, @PathVariable Long id) {
        return vacancyAdminService.editVacancy(vacancyCreateDto, id);
    }

    /**
     * Удаление вакансии по ID
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удаление вакансии по ID", security = {@SecurityRequirement(name = "JWT")})
    public void deleteVacancy(@PathVariable Long id) {
        vacancyAdminService.deleteVacancy(id);
    }

    /**
     * Поиск вакансий с возможностью фильтрации по ключевому слову и признаку наличия откликов
     */
    @GetMapping("/search")
    @Operation(description = "Поиск вакансий с фильтрацией по ключевому слову и откликам", security = {@SecurityRequirement(name = "JWT")})
    public List<VacancyAdminDto> findVacancies(@RequestParam Integer pageNumber, @RequestParam String keyWord, @RequestParam boolean isResponsed) {
        return vacancyAdminService.findVacancies(pageNumber, keyWord, isResponsed);
    }

    @GetMapping("/")
    @Operation(description = "Get vacancy and all respond users on it", security = {@SecurityRequirement(name = "JWT")})
    public List<VacancyAdminDto> getVacanciesAndRespondOnIt(@Param("pageNumber") Integer pageNumber) {
        return vacancyAdminService.getVacanciesWithRespondOnIt(pageNumber);
    }

    @GetMapping("/byId/{id}")
    @Operation(description = "Получить вакансию")
    public VacancyAdminDto getVacancyAdmin(@PathVariable("id") Long id) {
        return vacancyAdminService.getVacancyById(id);
    }
}