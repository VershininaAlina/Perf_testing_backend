package ru.alina.hr_backend_server.mapper.impl;

import ru.alina.hr_backend_server.dto.test.QuestionDto;
import ru.alina.hr_backend_server.entity.question.Question;
import ru.alina.hr_backend_server.mapper.AnswerMapper;
import ru.alina.hr_backend_server.mapper.QuestionMapper;

import java.util.ArrayList;
import java.util.List;

public class QuestionMapperImpl implements QuestionMapper {

    private final AnswerMapper answerMapper;

    public QuestionMapperImpl(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    @Override
    public QuestionDto questionDtoFromQuestion(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionType(question.getQuestionType());
        questionDto.setAnswerType(question.getAnswerType());
        questionDto.setId(question.getId());
        questionDto.setQuestion(question.getQuestion());
        questionDto.setAnswerDtoList(answerMapper.answerDtosFromAnswerList(question.getOptions()));
        return questionDto;
    }

    @Override
    public QuestionDto questionDtoFromQuestionWithAnswer(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestionType(question.getQuestionType());
        questionDto.setAnswerType(question.getAnswerType());
        questionDto.setId(question.getId());
        questionDto.setQuestion(question.getQuestion());
        questionDto.setAnswerDtoList(answerMapper.answerDtosFromAnswerList(question.getOptions()));
        questionDto.setCorrectAnswerDtoList(answerMapper.answerDtosFromAnswerList(question.getAnswers()));
        return questionDto;
    }

    @Override
    public List<QuestionDto> questionDtosFromQuestionList(List<Question> questions) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            questionDtos.add(questionDtoFromQuestion(question));
        }
        return questionDtos;
    }

    @Override
    public List<QuestionDto> questionDtosFromQuestionListWithAnswer(List<Question> questions) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            questionDtos.add(questionDtoFromQuestionWithAnswer(question));
        }
        return questionDtos;
    }
}
