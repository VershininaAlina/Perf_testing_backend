package ru.alina.hr_backend_server.mapper.impl;

import ru.alina.hr_backend_server.dto.test.TestDto;
import ru.alina.hr_backend_server.entity.test.Test;
import ru.alina.hr_backend_server.mapper.QuestionMapper;
import ru.alina.hr_backend_server.mapper.TestMapper;

import java.util.ArrayList;
import java.util.List;

public class TestMapperImpl implements TestMapper {

    private final QuestionMapper questionMapper;

    public TestMapperImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public TestDto testDtoFromTest(Test test) {
        TestDto testDto = new TestDto();
        testDto.setId(test.getId());
        testDto.setDescription(test.getDescription());
        testDto.setName(test.getName());
        testDto.setQuestionDtos(questionMapper.questionDtosFromQuestionList(test.getQuestions()));
        return testDto;
    }

    @Override
    public TestDto testDtoFromTestWithAnswer(Test test) {
        TestDto testDto = new TestDto();
        testDto.setId(test.getId());
        testDto.setDescription(test.getDescription());
        testDto.setName(test.getName());
        testDto.setQuestionDtos(questionMapper.questionDtosFromQuestionListWithAnswer(test.getQuestions()));
        return testDto;
    }

    @Override
    public List<TestDto> testDtosFromTestList(List<Test> tests) {
        List<TestDto> testDtos = new ArrayList<>();
        for (Test test : tests) {
            testDtos.add(testDtoFromTest(test));
        }
        return testDtos;
    }
}
