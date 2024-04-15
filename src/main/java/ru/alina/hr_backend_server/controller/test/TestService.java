package ru.alina.hr_backend_server.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.dto.test.UserAnswerDto;
import ru.alina.hr_backend_server.entity.answer.question.Answer;
import ru.alina.hr_backend_server.entity.answer.user.UserAnswer;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.repository.TestRepository;
import ru.alina.hr_backend_server.repository.UserAnswerRepository;
import ru.alina.hr_backend_server.repository.UserRepository;
import ru.alina.hr_backend_server.repository.UserTestPassedRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestService {
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final UserTestPassedRepository userTestPassedRepository;
    private final UserAnswerRepository userAnswerRepository;

    public void setAnswer(Authentication authentication, Long testId, UserAnswerDto userAnswerDto) {

        var current = userRepository.findUserByEmail(authentication.getName()).get();

        var test = testRepository.getTestById(testId);
        if (test == null) {
            throw new NotFoundException("Test not found");
        }

        var userTestPassing = userTestPassedRepository.getUserTestPassByTestAndUser(test, current);

        if (userTestPassing == null) {
            throw new NotFoundException("You still not responsed on vacancy");
        }

        var question = test.getQuestionById(userAnswerDto.getQuestionId());

        if (question == null) {
            throw new NotFoundException("Question not found");
        }

        List<Answer> answerList = new ArrayList<>();
        for (Long id : userAnswerDto.getAnswersId()) {
            var answer = question.getAnswerById(id);
            if (answer == null) {
                continue;
            }
            answerList.add(answer);
        }


        var userAnswer = new UserAnswer(question, answerList);
        userAnswerRepository.save(userAnswer);

        var result = userTestPassing.addUserAnswer(userAnswer);
        userTestPassedRepository.save(userTestPassing);
        if (result != null) {
            userAnswerRepository.delete(result);
        }
    }
}
