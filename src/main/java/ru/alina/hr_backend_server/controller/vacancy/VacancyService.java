package ru.alina.hr_backend_server.controller.vacancy;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.dto.vacancy.VacancyDto;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.mapper.VacancyMapper;
import ru.alina.hr_backend_server.repository.VacancyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final static Integer PAGE_SIZE = 30;

    private final VacancyRepository vacancyRepository;

    private final VacancyMapper vacancyMapper;

    public List<VacancyDto> findAllVacancy(int pageNumber) {
        List<VacancyDto> vacancyDtos = new ArrayList<>();

        Pageable page = PageRequest.of(pageNumber, PAGE_SIZE);
        List<Vacancy> vacancies = vacancyRepository.findAll(page).stream().toList();

        for (Vacancy vacancy : vacancies) {
            vacancyDtos.add(vacancyMapper.vacancyDtoFromVacancy(vacancy));
        }
        return vacancyDtos;
    }


    public Long count() {
        return vacancyRepository.count();
    }

    public VacancyDto getVacancyById(Long id) {
        Vacancy vacancy = vacancyRepository.getVacancyById(id);
        if (vacancy == null) {
            throw new NotFoundException("Vacancy not found");
        }
        return vacancyMapper.vacancyDtoFromVacancy(vacancy);
    }
}
