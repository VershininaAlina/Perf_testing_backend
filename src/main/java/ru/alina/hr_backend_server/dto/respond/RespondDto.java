package ru.alina.hr_backend_server.dto.respond;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.dto.vacancy.VacancyDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespondDto {
    private List<VacancyDto> vacancies;
}
