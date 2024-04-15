package ru.alina.hr_backend_server.mapper;

import ru.alina.hr_backend_server.dto.test.QuestionDto;
import ru.alina.hr_backend_server.entity.question.Question;

import java.util.List;

public interface QuestionMapper {

    QuestionDto questionDtoFromQuestion(Question question);

    QuestionDto questionDtoFromQuestionWithAnswer(Question question);

    List<QuestionDto> questionDtosFromQuestionList(List<Question> questions);

    List<QuestionDto> questionDtosFromQuestionListWithAnswer(List<Question> questions);
}
