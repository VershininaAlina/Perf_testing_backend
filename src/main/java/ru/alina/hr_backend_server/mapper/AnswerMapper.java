package ru.alina.hr_backend_server.mapper;

import ru.alina.hr_backend_server.dto.test.AnswerDto;
import ru.alina.hr_backend_server.entity.answer.question.Answer;

import java.util.List;

public interface AnswerMapper {

    AnswerDto answerDtoFromAnswer(Answer answer);

    List<AnswerDto> answerDtosFromAnswerList(List<Answer> answers);
}
