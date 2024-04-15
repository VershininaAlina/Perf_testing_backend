package ru.alina.hr_backend_server.controller.respond;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alina.hr_backend_server.dto.test.TestDto;

@Validated
@RestController
@RequiredArgsConstructor
public class RespondController {
    private final RespondService respondService;

    @Operation(summary = "Respond to a job vacancy",
            security = @SecurityRequirement(name = "JWT"),
            description = "Respond to a job vacancy and add the user to the response list")
    @PostMapping("/respond/vacancy")
    public void respond(@RequestParam Long idVacancy, Authentication authentication) {
        respondService.respond(authentication, idVacancy);
    }


    @Operation(summary = "Start a job test",
            security = @SecurityRequirement(name = "JWT"),
            description = "Start a job test for a given user and test")
    @PostMapping("/respond/start-test")
    public TestDto startTest(@RequestParam Long idTest, @RequestParam Long idVacancy, Authentication authentication) {
        return respondService.startTest(authentication, idTest, idVacancy);
    }

    @PostMapping("/respond/end-test")
    @Operation(summary = "Complete a job test",
            security = @SecurityRequirement(name = "JWT"),
            description = "Complete a job test and update the user's test status")
    public void endTest(Authentication authentication, @RequestParam Long idTest, @RequestParam Long idVacancy) {
        respondService.endTest(authentication, idTest, idVacancy);
    }



}
