package ru.alina.hr_backend_server.controller.vacancy;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alina.hr_backend_server.dto.vacancy.VacancyDto;

import java.util.List;

@Valid
@RequiredArgsConstructor
@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;

    @Operation(summary = "Get a list of all vacancies",
            description = "Get a list of all vacancies with the option of defining a page number",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping
    public ResponseEntity<List<VacancyDto>> getAllVacancies(@RequestParam(defaultValue = "0") int pageNumber) {
        List<VacancyDto> vacancies = vacancyService.findAllVacancy(pageNumber);
        return ResponseEntity.ok(vacancies);
    }


    @Operation(summary = "Get the number of vacancies",
            security = @SecurityRequirement(name = "JWT"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/count")
    public ResponseEntity<Long> countVacancies() {
        Long count = vacancyService.count();
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Get vacancy by id",
            security = @SecurityRequirement(name = "JWT")
    )
    @GetMapping("/{id}")
    public ResponseEntity<VacancyDto> getVacancy(@PathVariable("id") Long id) {
        return ResponseEntity.ok(vacancyService.getVacancyById(id));
    }
}