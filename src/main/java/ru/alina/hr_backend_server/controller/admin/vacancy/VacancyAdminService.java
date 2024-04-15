package ru.alina.hr_backend_server.controller.admin.vacancy;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.dto.user_test_passed.UserTestPassedDtos;
import ru.alina.hr_backend_server.dto.user_test_passed.UserVacancyTestPassed;
import ru.alina.hr_backend_server.dto.vacancy.VacancyAdminDto;
import ru.alina.hr_backend_server.dto.vacancy.VacancyCreateDto;
import ru.alina.hr_backend_server.dto.vacancy.VacancyDto;
import ru.alina.hr_backend_server.dto.video.VideoDto;
import ru.alina.hr_backend_server.entity.answer.question.Answer;
import ru.alina.hr_backend_server.entity.answer.user.UserAnswer;
import ru.alina.hr_backend_server.entity.question.AnswerType;
import ru.alina.hr_backend_server.entity.question.Question;
import ru.alina.hr_backend_server.entity.question.QuestionType;
import ru.alina.hr_backend_server.entity.response.ResponseVacancy;
import ru.alina.hr_backend_server.entity.test.Test;
import ru.alina.hr_backend_server.entity.test.UserTestPass;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.mapper.TestMapper;
import ru.alina.hr_backend_server.mapper.UserMapper;
import ru.alina.hr_backend_server.mapper.VacancyMapper;
import ru.alina.hr_backend_server.repository.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class VacancyAdminService {
    private final static Integer PAGE_SIZE = 30;

    private final VacancyRepository vacancyRepository;
    private final TestRepository testRepository;
    private final ResponseVacancyRepository responseVacancyRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final UserTestPassedRepository userTestPassedRepository;


    private final UserMapper userMapper;
    private final VacancyMapper vacancyMapper;
    private final TestMapper testMapper;

    public VacancyAdminDto getVacancyById(Long id) {

        var vacancy = vacancyRepository.getVacancyById(id);
        if (vacancy == null) {
            throw new NotFoundException("Vacancy not found");
        }

        return getVacancyAdminDto(vacancy);
    }

    public List<VacancyAdminDto> getVacanciesWithRespondOnIt(int pageNumber) {
        List<VacancyAdminDto> vacancyDtos = new ArrayList<>();

        Pageable page = PageRequest.of(pageNumber, PAGE_SIZE);
        List<Vacancy> vacancies = vacancyRepository.findAll(page).stream().toList();


        for (Vacancy vacancy : vacancies) {
            var dtos = getVacancyAdminDto(vacancy);

            vacancyDtos.add(dtos);
        }

        return vacancyDtos;
    }

    private VacancyAdminDto getVacancyAdminDto(Vacancy vacancy) {
        var dtos = vacancyMapper.fromVacancyToVacancyAdmin(vacancy);

        Integer numberOfPassedTest = 0;
        Integer numberOfResponded = 0;


        //vacancy id, UserTestPassedDtos
        HashMap<Long, UserTestPassedDtos> userTestPassedDtosHashMap = new HashMap<>();


        // get responded users
        var responseVacancies = responseVacancyRepository.findAllByVacancy(vacancy);
        // plus number of responded users
        numberOfResponded += responseVacancies.size();

        for (ResponseVacancy responseVacancy : responseVacancies) {

            // get user of responded
            var user = responseVacancy.getUser();

            // check if exist userTestPassedDtos and if not then create
            UserTestPassedDtos userTestPassedDtos = userTestPassedDtosHashMap.get(user.getId());
            if (userTestPassedDtos == null) {

                userTestPassedDtos = new UserTestPassedDtos();
                userTestPassedDtos.setUserDto(userMapper.userDtoFromUser(user));
                userTestPassedDtos.setUserVacancyTestPasseds(new ArrayList<>());

                userTestPassedDtosHashMap.put(user.getId(), userTestPassedDtos);
            }
            // get passed tests of vacancy
            var userTestPassed = userTestPassedRepository.getUserTestPassByVacancyAndUser(user, vacancy);

            // calc passed tests
            numberOfPassedTest += userTestPassed.size();


            for (UserTestPass userTestPass : userTestPassed) {
                // check passed test and check answers
                UserVacancyTestPassed userVacancyTestPassed = new UserVacancyTestPassed();
                userVacancyTestPassed.setTestDto(testMapper.testDtoFromTestWithAnswer(userTestPass.getTest()));

                var video = userTestPass.getVideo();
                if (video != null) {
                    VideoDto videoDto = new VideoDto(video.getId(), video.getFileName());
                    userVacancyTestPassed.setVideo(videoDto);

                }

                // get answers of user
                var userAnswers = userTestPass.getUserAnswers();

                // get some test to get answers
                var test = userTestPass.getTest();

                // init number of correct and incorrect answers
                int numberOfCorrect = 0;
                int numberOfInCorrect = 0;

                // get question(answers list) only text question
                HashMap<String, List<Answer>> listHashMap = new HashMap<>();
                for (Question question : test.getQuestions()) {
                    if (question.getQuestionType() != QuestionType.VOICE) {
                        listHashMap.put(question.getQuestion(), question.getAnswers());
                    }
                }
                for (Map.Entry<String, List<Answer>> entry : listHashMap.entrySet()) {

                }
                // check valid answers
                for (UserAnswer userAnswer : userAnswers) {
                    // read answers list by question
                    String question = userAnswer.getQuestion().getQuestion();
                    var answers = listHashMap.get(question);
                    // if type answer is one then this valid
                    if (userAnswer.getQuestion().getAnswerType() == AnswerType.ONE) {

                        var answerCorrect = answers.get(0);
                        var answerUserUnKnown = userAnswer.getAnswers().get(0);
                        System.out.println(answerCorrect.getId() + " " + answerUserUnKnown.getId());
                        if (answerCorrect.getId() == answerUserUnKnown.getId()) {
                            numberOfCorrect += 1;
                        } else {
                            numberOfInCorrect += 1;
                        }
                    } else {
                        // if a lot of answers
                        int matchCount = 0;


                        for (Answer answer : answers) {
                            for (Answer usrAnswer : userAnswer.getAnswers()) {
                                if (answer.getId() == usrAnswer.getId()) {
                                    matchCount += 1;
                                }
                            }
                        }

                        if (matchCount == answers.size() && answers.size() == userAnswer.getAnswers().size()) {
                            numberOfCorrect += 1;
                        } else {
                            numberOfInCorrect += 1;
                        }
                    }
                }

                // save count correct and incorrect answers
                userVacancyTestPassed.setNumberOfCorrectAnswer(numberOfCorrect);
                userVacancyTestPassed.setNumberOfIncorrect(numberOfInCorrect);
                // add this answers in answer
                userTestPassedDtos.getUserVacancyTestPasseds().add(userVacancyTestPassed);
            }
        }

        dtos.setNumberOfResponded(numberOfResponded);
        dtos.setNumberOfPassedTests(numberOfPassedTest);

        List<UserTestPassedDtos> userTestPassedDtos = new ArrayList<>();
        for (Map.Entry<Long, UserTestPassedDtos> userTestPassedDtosEntry : userTestPassedDtosHashMap.entrySet()) {
            userTestPassedDtos.add(userTestPassedDtosEntry.getValue());
        }

        dtos.setUserTestPassedDtos(userTestPassedDtos);
        return dtos;
    }


    public VacancyDto createVacancy(VacancyCreateDto vacancyCreateDto) {
        var vacancy = vacancyMapper.fromVacancyCreateDtoToVacancy(vacancyCreateDto);
        vacancyRepository.save(vacancy);
        return vacancyMapper.vacancyDtoFromVacancy(vacancy);
    }

    public VacancyDto editVacancy(VacancyCreateDto vacancyCreateDto, Long id) {
        var vacancy = vacancyRepository.getVacancyById(id);
        if (vacancy == null) {
            throw new NotFoundException("Vacancy not found");
        }
        vacancy.setName(vacancyCreateDto.getName());
        vacancy.setDescription(vacancyCreateDto.getDescription());
        vacancy.setResponsibilities(vacancyCreateDto.getResponsibilities());
        vacancy.setRequirements(vacancyCreateDto.getRequirements());
        vacancy.setWillPlus(vacancyCreateDto.getWillPlus());
        vacancy.setConditions(vacancyCreateDto.getConditions());
        vacancyRepository.save(vacancy);
        return vacancyMapper.vacancyDtoFromVacancy(vacancy);
    }

    public void deleteVacancy(Long id) {
        var vacancy = vacancyRepository.getVacancyById(id);
        if (vacancy == null) {
            throw new NotFoundException("Vacancy not found");
        }


        vacancy.setTests(new ArrayList<>());
        vacancyRepository.save(vacancy);


        List<ResponseVacancy> responseVacancies = responseVacancyRepository.findAllByVacancy(vacancy);

        var tests = testRepository.findAllByVacancy(vacancy);

        var userTestPassed = userTestPassedRepository.findAllByTests(tests);
        for (UserTestPass userTestPass : userTestPassed) {
            userTestPass.setVideo(null);
            userTestPass.setUser(null);

            var userAnswers = userTestPass.getUserAnswers();

            List<UserAnswer> userAnswerList = new ArrayList<>();
            userAnswerList.addAll(userAnswers);

            userTestPass.setUserAnswers(new ArrayList<>());
            userTestPassedRepository.save(userTestPass);


            for (UserAnswer userAnswer : userAnswerList) {
                userAnswer.setQuestion(null);
                userAnswer.setAnswers(new ArrayList<>());
                userAnswerRepository.save(userAnswer);
                userAnswerRepository.delete(userAnswer);
            }
            userTestPassedRepository.delete(userTestPass);
        }
        for (Test test : tests) {
            test.setVacancy(null);
            testRepository.save(test);
            testRepository.delete(test);
        }

        vacancy.setResponseVacancyUser(new ArrayList<>());
        vacancyRepository.save(vacancy);

        responseVacancyRepository.deleteAll(responseVacancies);


        // and delete some vacancy
        vacancyRepository.delete(vacancy);
    }

    public List<VacancyAdminDto> findVacancies(Integer pageNumber, String keyWord, boolean isResponsed) {
        Pageable pageable = PageRequest.of(pageNumber, 30);
        Page<Vacancy> vacanciesPage;

        if (isResponsed) {
            vacanciesPage = vacancyRepository.vacancyGet(keyWord, 0, pageable);
        } else {
            vacanciesPage = vacancyRepository.vacancyGet(keyWord, pageable);
        }

        List<Vacancy> vacancies = vacanciesPage.getContent();
        return vacancyMapper.fromVacancyListToVacancyAdminList(vacancies);
    }

    // test
    public static void main(String[] args) {
        List<Integer> answer = Arrays.asList(1, 2, 4);
        List<Integer> userAnswer = Arrays.asList(2, 3, 4);

        int matchCount = 0;

        for (Integer an : answer) {
            for (Integer us : userAnswer) {
                if (an == us) {
                    matchCount += 1;
                }
            }
        }


        double similarity = (double) matchCount / answer.size() * 100;
        System.out.println("Совпадение: " + similarity + "%");

    }


}
