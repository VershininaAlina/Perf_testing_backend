package ru.alina.hr_backend_server.controller.experience;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alina.hr_backend_server.dto.error.ApiError;
import ru.alina.hr_backend_server.dto.experience.ExperienceDto;
import ru.alina.hr_backend_server.entity.profile.Experience;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(description = "Add,exit and remove experience part",name = "Experience controller")
public class ExperienceController {


    private final ExperienceService experienceService;

    @Operation(summary = "Add an experience", description = "Add an experience for a user",
            security = {
                    @SecurityRequirement(name = "JWT")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExperienceDto.class))
            )
    )
    @PostMapping("/api/experience")
    public void addExperience(@Valid @RequestBody ExperienceDto experienceDto, Authentication authentication) {
        experienceService.addExperience(authentication, experienceDto);
    }


    @Operation(summary = "Edit an experience", description = "Edit an experience for a user",
            security = {
                    @SecurityRequirement(name = "JWT")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExperienceDto.class))
            ),
            parameters = @Parameter(name = "id", description = "Experience id", schema = @Schema(type = "Long")))
    @PostMapping("/api/experience/{id}")
    public void editExperience(@PathVariable Long id,@Valid  @RequestBody ExperienceDto experienceDto, Authentication authentication) {
        experienceService.editExperience(authentication, experienceDto, id);
    }

    @Operation(summary = "Delete an experience", description = "Delete an experience for a user",
            security = {
                    @SecurityRequirement(name = "JWT")
            },
            parameters = @Parameter(name = "id", description = "Experience id", schema = @Schema(type = "Long")))
    @DeleteMapping("/api/experience/{id}")
    public void deleteExperience(@PathVariable Long id, Authentication authentication) {
        experienceService.deleteExperience(authentication, id);
    }

}