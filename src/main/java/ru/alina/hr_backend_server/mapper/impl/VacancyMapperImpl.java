package ru.alina.hr_backend_server.mapper.impl;

import ru.alina.hr_backend_server.dto.vacancy.VacancyAdminDto;
import ru.alina.hr_backend_server.dto.vacancy.VacancyCreateDto;
import ru.alina.hr_backend_server.dto.vacancy.VacancyDto;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;
import ru.alina.hr_backend_server.mapper.TestMapper;
import ru.alina.hr_backend_server.mapper.UserMapper;
import ru.alina.hr_backend_server.mapper.VacancyMapper;

import java.util.ArrayList;
import java.util.List;

public class VacancyMapperImpl implements VacancyMapper {

    private final TestMapper testMapper;
    private final UserMapper userMapper;


    public VacancyMapperImpl(TestMapper testMapper, UserMapper userMapper) {
        this.testMapper = testMapper;
        this.userMapper = userMapper;
    }

    @Override
    public VacancyDto vacancyDtoFromVacancy(Vacancy vacancy) {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setCondition(vacancy.getConditions());
        vacancyDto.setId(vacancy.getId());
        vacancyDto.setDescription(vacancy.getDescription());
        vacancyDto.setName(vacancy.getName());
        vacancyDto.setRequirements(vacancy.getRequirements());
        vacancyDto.setResponsibilities(vacancy.getResponsibilities());
        vacancyDto.setWillPlus(vacancy.getWillPlus());
        vacancyDto.setTestDtos(testMapper.testDtosFromTestList(vacancy.getTests()));

        return vacancyDto;
    }

    @Override
    public List<VacancyDto> vacancyDtosFromVacancyList(List<Vacancy> vacancies) {
        List<VacancyDto> vacancyDtos = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            vacancyDtos.add(vacancyDtoFromVacancy(vacancy));
        }
        return vacancyDtos;
    }

    @Override
    public Vacancy fromVacancyCreateDtoToVacancy(VacancyCreateDto vacancyCreateDto) {
        var vacancy = new Vacancy();
        vacancy.setName(vacancyCreateDto.getName());
        vacancy.setDescription(vacancyCreateDto.getDescription());
        vacancy.setResponsibilities(vacancyCreateDto.getResponsibilities());
        vacancy.setRequirements(vacancyCreateDto.getRequirements());
        vacancy.setWillPlus(vacancyCreateDto.getWillPlus());
        vacancy.setConditions(vacancyCreateDto.getConditions());
        return vacancy;
    }

    @Override
    public VacancyAdminDto fromVacancyToVacancyAdmin(Vacancy vacancy) {
        VacancyAdminDto vacancyDto = new VacancyAdminDto();
        vacancyDto.setCondition(vacancy.getConditions());
        vacancyDto.setId(vacancy.getId());
        vacancyDto.setDescription(vacancy.getDescription());
        vacancyDto.setName(vacancy.getName());
        vacancyDto.setRequirements(vacancy.getRequirements());
        vacancyDto.setResponsibilities(vacancy.getResponsibilities());
        vacancyDto.setWillPlus(vacancy.getWillPlus());
        vacancyDto.setTestDtos(testMapper.testDtosFromTestList(vacancy.getTests()));
        vacancyDto.setResponseVacancyUser(userMapper.userDtoListFromUserList(vacancy.getResponseVacancyUser()));
        return vacancyDto;
    }

    @Override
    public List<VacancyAdminDto> fromVacancyListToVacancyAdminList(List<Vacancy> vacancies) {
        List<VacancyAdminDto> vacancyAdminDtos = new ArrayList<>();
        for (Vacancy vacancy : vacancies) {
            vacancyAdminDtos.add(fromVacancyToVacancyAdmin(vacancy));
        }
        return vacancyAdminDtos;
    }
}
