package ru.alina.hr_backend_server.mapper;

import ru.alina.hr_backend_server.dto.test.TestDto;
import ru.alina.hr_backend_server.entity.test.Test;

import java.util.List;

public interface TestMapper {
    TestDto testDtoFromTest(Test test);
    TestDto testDtoFromTestWithAnswer(Test test);

    List<TestDto> testDtosFromTestList(List<Test> tests);
}
