package ru.alina.hr_backend_server.controller.test;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alina.hr_backend_server.dto.test.UserAnswerDto;

@Validated
@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @PostMapping("/test/answer")
    public void setAnswer(Authentication authentication, @RequestParam Long testId, @Valid @RequestBody UserAnswerDto userAnswerDto) {
        testService.setAnswer(authentication, testId, userAnswerDto);
    }

}
