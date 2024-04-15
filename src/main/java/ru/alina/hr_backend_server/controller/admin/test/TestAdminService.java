package ru.alina.hr_backend_server.controller.admin.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.dto.test.QuestionDto;
import ru.alina.hr_backend_server.dto.test.TestDto;
import ru.alina.hr_backend_server.dto.test.create.AnswerCreateDto;
import ru.alina.hr_backend_server.dto.test.create.QuestionCreateDto;
import ru.alina.hr_backend_server.dto.test.create.TestCreateDto;
import ru.alina.hr_backend_server.entity.answer.question.Answer;
import ru.alina.hr_backend_server.entity.question.Question;
import ru.alina.hr_backend_server.entity.test.Test;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.mapper.TestMapper;
import ru.alina.hr_backend_server.repository.AnswerRepository;
import ru.alina.hr_backend_server.repository.QuestionRepository;
import ru.alina.hr_backend_server.repository.TestRepository;
import ru.alina.hr_backend_server.repository.VacancyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestAdminService {

    private final VacancyRepository vacancyRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private final TestMapper testMapper;


    TestDto createTest(TestCreateDto testCreateDto, Long id) {

        Vacancy vacancy = vacancyRepository.getVacancyById(id);
        if (vacancy == null) {
            throw new NotFoundException("Vacancy not found");
        }

        var test = new Test();
        test.setVacancy(vacancy);

        test.setName(testCreateDto.getName());
        test.setDescription(testCreateDto.getDescription());


        List<Question> questions = new ArrayList<>();

        for (QuestionCreateDto questionCreateDto : testCreateDto.getQuestionDtos()) {
            List<Answer> answers = new ArrayList<>();
            List<Answer> options = new ArrayList<>();

            for (AnswerCreateDto optionCreateDto : questionCreateDto.getOptions()) {


                var answer = new Answer();
                answer.setAnswer(optionCreateDto.getAnswer());
                answerRepository.save(answer);

                for (AnswerCreateDto answerCreateDto : questionCreateDto.getAnswer()) {
                    if (optionCreateDto.getAnswer().equals(answerCreateDto.getAnswer())) {
                        answers.add(answer);
                        break;
                    }
                }
                options.add(answer);
            }
            var question = new Question();
            question.setQuestion(questionCreateDto.getQuestion());
            question.setAnswers(answers);
            question.setOptions(options);
            question.setAnswerType(questionCreateDto.getAnswerType());
            question.setQuestionType(questionCreateDto.getQuestionType());
            questionRepository.save(question);
            questions.add(question);
        }
        test.setQuestions(questions);
        testRepository.save(test);


        vacancy.getTests().add(test);
        vacancyRepository.save(vacancy);
        return testMapper.testDtoFromTest(test);
    }

    public void deleteTestFromVacancy(Long vacancyId, Long testId) {
        Vacancy vacancy = vacancyRepository.getVacancyById(vacancyId);
        if (vacancy == null) {
            throw new NotFoundException("Vacancy not found");
        }

        for (Test test : vacancy.getTests()) {
            if (test.getId() == testId) {
                vacancy.getTests().remove(test);
                testRepository.delete(test);
                vacancyRepository.save(vacancy);
                break;

            }
        }
    }
}
