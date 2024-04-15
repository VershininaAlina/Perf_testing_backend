package ru.alina.hr_backend_server.controller.profile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.alina.hr_backend_server.dto.profile.EditProfileDto;
import ru.alina.hr_backend_server.dto.user.UserDto;
import ru.alina.hr_backend_server.dto.user.UserMeDto;
import ru.alina.hr_backend_server.dto.user_test_passed.UserTestPassedDto;
import ru.alina.hr_backend_server.entity.test.UserTestPass;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "ProfileController", description = "Profile controller to update. Also for get profiles image")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "Update profile", security = {@SecurityRequirement(name = "JWT")}, tags = "Profile")
    @PostMapping("/profile")
    public ResponseEntity<Void> editUserProfile(@Valid @RequestBody EditProfileDto editProfileDto, Authentication authentication) {
        profileService.editUserProfile(authentication, editProfileDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Uplaod Avatar of profile", security = {@SecurityRequirement(name = "JWT")}, tags = "Profile", responses = {@ApiResponse(responseCode = "200", description = "OK")})
    @PostMapping("/profile/avatar")
    public ResponseEntity<Void> uploadAvatar(MultipartFile file, Authentication authentication) {
        profileService.uploadAvatar(authentication, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get Avatar(Some Images)", security = {@SecurityRequirement(name = "JWT")}, tags = "Profile", responses = {@ApiResponse(responseCode = "200", description = "Avatar image file")})
    @GetMapping("/profile/avatar")
    public ResponseEntity<Resource> getAvatar(@Param("fileName") String fileName) {
        return profileService.getAvatar(fileName);
    }

    @Operation(summary = "Get me", security = {@SecurityRequirement(name = "JWT")})
    @GetMapping("/profile")
    public ResponseEntity<UserMeDto> getMe(Authentication authentication) {
        return ResponseEntity.ok(profileService.getMe(authentication));

    }


    @Operation(summary = "Get user profile by id", security = {@SecurityRequirement(name = "JWT")})
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserMeDto> getProfileById(@PathVariable("id") Long id, Authentication authentication) {
        return ResponseEntity.ok(profileService.getById(id));

    }

    @GetMapping("/profile/my-test-passed")
    public ResponseEntity<List<UserTestPassedDto>> getMyTestPassed(Authentication authentication) {
        return ResponseEntity.ok(profileService.getMyTestPassed(authentication));
    }
}
