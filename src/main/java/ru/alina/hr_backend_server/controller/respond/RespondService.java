package ru.alina.hr_backend_server.controller.respond;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.dto.test.TestDto;
import ru.alina.hr_backend_server.dto.user.UserDto;
import ru.alina.hr_backend_server.dto.websocket.TestEndDto;
import ru.alina.hr_backend_server.entity.test.Test;
import ru.alina.hr_backend_server.entity.test.UserTestPass;
import ru.alina.hr_backend_server.entity.test.UserTestStatus;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.exception.AlreadyExistException;
import ru.alina.hr_backend_server.exception.BadRequestException;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.mapper.TestMapper;
import ru.alina.hr_backend_server.repository.*;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RespondService {
    private final ResponseVacancyRepository responseVacancyRepository;
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final UserTestPassedRepository userTestPassedRepository;

    private final TestMapper testMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void respond(Authentication authentication, Long idVacancy) {
        var current = getCurrent(authentication);

        var vacancy = vacancyRepository.getVacancyById(idVacancy);
        if (vacancy == null) {
            throw new NotFoundException("Vacancy not found");
        }


        for (User user : vacancy.getResponseVacancyUser()) {
            if (user.getId() == current.getId()) {
                throw new AlreadyExistException("User already responded on this vacancy");
            }
        }

        vacancy.getResponseVacancyUser().add(current);
        vacancyRepository.save(vacancy);

        var responseVacancy = new ru.alina.hr_backend_server.entity.response.ResponseVacancy(vacancy, current, new Date());
        this.responseVacancyRepository.save(responseVacancy);
    }


    public TestDto startTest(Authentication authentication, Long idTest, Long idVacancy) {
        var current = getCurrent(authentication);

        if (userTestPassedRepository.getUserTestPassByUserAndTimeEndIsNull(current).size() > 0) {
            throw new BadRequestException("You already started test");
        }
        var vacancy = vacancyRepository.getVacancyById(idVacancy);
        if (vacancy == null) {
            throw new NotFoundException("Vacancy not found");
        }

        var test = testRepository.getTestById(idTest);
        if (test == null) {
            throw new NotFoundException("Test not found");
        }

        var responseVacancy = responseVacancyRepository.getResponseVacanciesByUserAndVacancy(current, vacancy);
        if (responseVacancy == null) {
            throw new BadRequestException("You still not responsed on vacancy");
        }
        var userTestPassed = userTestPassedRepository.getUserTestPassByTestAndUser(test, current);
        if (userTestPassed != null) {
            if (userTestPassed.getUserTestStatus() == UserTestStatus.PASSED) {
                throw new AlreadyExistException("User test already passed");
            } else {
                throw new AlreadyExistException("User test already started");
            }
        }

        //
        boolean flag = false;

        for (Test currentTest : vacancy.getTests()) {
            if (currentTest.getId() == test.getId()) {
                flag = true;
                break;
            }
        }

        if (flag == false) {
            throw new NotFoundException("Test not found");
        }

        userTestPassed = new UserTestPass(current, test);
        userTestPassedRepository.save(userTestPassed);


        // TODO: add to access list of topics
        simpMessagingTemplate.convertAndSend("/topic/task/" + userTestPassed.getId() + "/", new UserDto());


        return testMapper.testDtoFromTest(test);
    }


    public void endTest(Authentication authentication, Long idTest, Long idVacancy) {
        var current = getCurrent(authentication);

        var test = testRepository.getTestById(idTest);
        if (test == null) {
            throw new NotFoundException("Test not found");
        }
        var userTestPassed = userTestPassedRepository.getUserTestPassByTestAndUser(test, current);
        if (userTestPassed == null) {
            throw new AlreadyExistException("User test not started");
        }

        userTestPassed.setUserTestStatus(UserTestStatus.PASSED);
        userTestPassed.setTimeEnd(new Date());
        userTestPassedRepository.save(userTestPassed);

        simpMessagingTemplate.convertAndSend("/topic/task/" + userTestPassed.getId() + "/", new TestEndDto());
    }

    private User getCurrent(Authentication authentication) {
        var userEmail = authentication.getName();
        var current = userRepository.findUserByEmail(userEmail).get();
        return current;
    }

}
