package ru.alina.hr_backend_server.controller.admin.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alina.hr_backend_server.dto.test.TestDto;
import ru.alina.hr_backend_server.dto.test.create.TestCreateDto;

@Validated
@RestController
@RequestMapping("/admin/vacancies/{vacancyId}/tests")
@RequiredArgsConstructor
public class TestAdminController {
    private final TestAdminService testAdminService;

    /**
     * Создание теста для вакансии по ID
     */
    @PostMapping
    @Operation(description = "Создание теста для вакансии по ID. Позволяет создать тест для указанной вакансии",
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    public TestDto createTest(Authentication authentication, @Valid @RequestBody TestCreateDto testCreateDto, @PathVariable Long vacancyId) {
        return testAdminService.createTest(testCreateDto, vacancyId);
    }

    /**
     * Удаление теста из вакансии по ID
     */
    @DeleteMapping("/{testId}")
    @Operation(description = "Удаление теста из вакансии по ID. Позволяет удалить тест из указанной вакансии",
            security = {
                    @SecurityRequirement(name = "JWT")
            }
    )
    public void deleteTestFromVacancy(Authentication authentication, @PathVariable Long vacancyId, @PathVariable Long testId) {
        testAdminService.deleteTestFromVacancy(vacancyId, testId);
    }
}
