package ru.alina.hr_backend_server.mapper;

import ru.alina.hr_backend_server.dto.vacancy.VacancyAdminDto;
import ru.alina.hr_backend_server.dto.vacancy.VacancyCreateDto;
import ru.alina.hr_backend_server.dto.vacancy.VacancyDto;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;

import java.util.List;

public interface VacancyMapper {
    VacancyDto vacancyDtoFromVacancy(Vacancy vacancy);

    List<VacancyDto> vacancyDtosFromVacancyList(List<Vacancy> vacancies);
    Vacancy fromVacancyCreateDtoToVacancy(VacancyCreateDto vacancyCreateDto);


    VacancyAdminDto fromVacancyToVacancyAdmin(Vacancy vacancy);

    List<VacancyAdminDto> fromVacancyListToVacancyAdminList(List<Vacancy> vacancies);

}
