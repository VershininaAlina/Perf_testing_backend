package ru.alina.hr_backend_server.controller.authentication;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;

import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import ru.alina.hr_backend_server.dto.auth.UserAuthDto;
import ru.alina.hr_backend_server.dto.auth.UserAuthenticationDto;
import ru.alina.hr_backend_server.dto.auth.UserRegistrationDto;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/signin")
    @Operation(
            summary = "Sign in to the application",
            description = "Authenticates a user and returns a JWT token",
            tags = {"Authentication"},
            /*security = {
                    @SecurityRequirement(name = "bearerAuth")
            },

             */

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserAuthenticationDto.class)
                    )
            ),

            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful authentication",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserAuthDto.class)
                            )

                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )

            }

    )
    public ResponseEntity<UserAuthDto> signIn(@Valid @RequestBody UserAuthenticationDto userAuthenticationDto) {

        return ResponseEntity.ok(authenticationService.signIn(userAuthenticationDto));

    }


    @PostMapping("/signup")
    @Operation(
            summary = "Sign up for the application",
            description = "Registers a new user and creates a new wallet",
            tags = {"Authentication"},
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
    public ResponseEntity<UserAuthDto> signUp(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {

        return ResponseEntity.ok(authenticationService.singUp(userRegistrationDto));

    }


}