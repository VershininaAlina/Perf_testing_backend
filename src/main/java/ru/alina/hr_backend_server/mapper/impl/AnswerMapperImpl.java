package ru.alina.hr_backend_server.mapper.impl;

import ru.alina.hr_backend_server.dto.test.AnswerDto;
import ru.alina.hr_backend_server.entity.answer.question.Answer;
import ru.alina.hr_backend_server.mapper.AnswerMapper;

import java.util.ArrayList;
import java.util.List;

public class AnswerMapperImpl implements AnswerMapper {
    @Override
    public AnswerDto answerDtoFromAnswer(Answer answer) {
        var answerDto = new AnswerDto();
        answerDto.setAnswer(answer.getAnswer());
        answerDto.setId(answer.getId());
        return answerDto;
    }

    @Override
    public List<AnswerDto> answerDtosFromAnswerList(List<Answer> answers) {
        List<AnswerDto> answerDtoList = new ArrayList<>();
        for (Answer answer : answers) {
            answerDtoList.add(answerDtoFromAnswer(answer));
        }
        return answerDtoList;
    }
}
