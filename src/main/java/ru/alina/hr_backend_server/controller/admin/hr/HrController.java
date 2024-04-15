package ru.alina.hr_backend_server.controller.admin.hr;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.alina.hr_backend_server.dto.auth.UserAuthDto;
import ru.alina.hr_backend_server.dto.auth.UserRegistrationDto;
import ru.alina.hr_backend_server.dto.user.UserDto;
import ru.alina.hr_backend_server.entity.user.Role;

import java.util.List;

@RequestMapping("/admin/hr")
@RestController
@RequiredArgsConstructor
public class HrController {

    private final HrService hrService;


    @PostMapping("/signup")
    @Operation(
            summary = "Sign up hr for the application",
            description = "Registers a new hr user",
            security = {@SecurityRequirement(name = "JWT")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful registration",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserAuthDto.class)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    mediaType = "application/json"
                            )

                    )

            }

    )
    public void signUp(Authentication authentication, @Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        hrService.singUp(userRegistrationDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers(Authentication authentication) {
        return ResponseEntity.ok(hrService.getUserHr());
    }


    @GetMapping("/edit")
    public void updateHrRole(Authentication authentication, @RequestParam("id") Long id, @RequestParam("role") Role role) {
        hrService.setUserRole(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(Authentication authentication, @PathVariable("id") Long id) {
        hrService.deleteUser(id);
    }
}
