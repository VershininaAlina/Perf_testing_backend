package ru.alina.hr_backend_server.controller.profile;

import jakarta.activation.FileTypeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.alina.hr_backend_server.dto.profile.EditProfileDto;
import ru.alina.hr_backend_server.dto.respond.RespondDto;
import ru.alina.hr_backend_server.dto.user.UserDto;
import ru.alina.hr_backend_server.dto.user.UserMeDto;
import ru.alina.hr_backend_server.dto.user_test_passed.UserTestPassedDto;
import ru.alina.hr_backend_server.entity.image.Image;
import ru.alina.hr_backend_server.entity.test.UserTestPass;
import ru.alina.hr_backend_server.entity.vacancy.Vacancy;
import ru.alina.hr_backend_server.exception.BadRequestException;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.mapper.UserMapper;
import ru.alina.hr_backend_server.mapper.VacancyMapper;
import ru.alina.hr_backend_server.repository.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private static final String IMAGES_FOLDER = "res/images/";


    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ImageRepository imageRepository;
    private final ResponseVacancyRepository responseVacancyRepository;
    private final UserTestPassedRepository userTestPassedRepository;


    private final UserMapper userMapper;
    private final VacancyMapper vacancyMapper;


    private final ResourceLoader resourceLoader;


    public void editUserProfile(Authentication authentication, EditProfileDto editProfileDto) {
        var user = userRepository.findUserByEmail(authentication.getName()).get();
        var profile = user.getProfile();
        profile.setName(editProfileDto.getName());
        profile.setSecondname(editProfileDto.getSecondname());
        profile.setLastname(editProfileDto.getLastname());
        profile.setBirthDay(editProfileDto.getBirthDay());
        profileRepository.save(profile);
    }

    public void uploadAvatar(Authentication authentication, MultipartFile uploadImage) {

        var user = userRepository.findUserByEmail(authentication.getName()).get();


        if (uploadImage.isEmpty()) {

            throw new BadRequestException("File is empty");

        }

        // Generate a unique filename for the uploaded file
        String fileName = IMAGES_FOLDER + UUID.randomUUID().toString() + "." + getFileExtension(uploadImage.getOriginalFilename());

        // Check if the file is an image
        if (!isImage(fileName)) {

            throw new BadRequestException("Invalid file type. Please upload a JPG, PNG, or GIF file.");
        }

        // Save the file to the images folder
        try {
            File file = new File(fileName);
            Path filePath = file.toPath();
            Files.copy(uploadImage.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestException("Failed to save the file");
        }

        var image = new Image(fileName);
        imageRepository.save(image);

        user.getProfile().setPhotoProfile(image);
        profileRepository.save(user.getProfile());
    }


    private String getFileExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }

        return extension;
    }


    private boolean isImage(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        return extension.equals("jpg") || extension.equals("png") || extension.equals("gif");
    }

    public ResponseEntity<Resource> getAvatar(String fileName) {
        try {
            File file = new File(IMAGES_FOLDER + fileName);
            Path filePath = file.toPath();

            Resource resource = new InputStreamResource(new FileInputStream(filePath.toFile()));

            MediaType mediaType = MediaType.parseMediaType(FileTypeMap.getDefaultFileTypeMap().getContentType(fileName));

            return ResponseEntity.ok()
                    .contentLength(filePath.toFile().length())
                    .contentType(mediaType)
                    .body(resource);

        } catch (IOException e) {
            throw new NotFoundException("Failed to load the image file");
        }


    }

    public UserMeDto getMe(Authentication authentication) {
        var email = authentication.getName();
        var user = userRepository.findUserByEmail(email).get();
        var list = responseVacancyRepository.respondVacanciesByUser(user);

        var dto = userMapper.userMeDtoFromUser(user);
        dto.setResponds(new RespondDto(vacancyMapper.vacancyDtosFromVacancyList(list)));

        return dto;
    }

    public List<UserTestPassedDto> getMyTestPassed(Authentication authentication) {
        var email = authentication.getName();
        var user = userRepository.findUserByEmail(email).get();
        var vacancies = responseVacancyRepository.respondVacanciesByUser(user);

        HashMap<Long, List<Long>> hashMap = new HashMap<>();


        List<UserTestPass> userTestPasses = userTestPassedRepository.getUserTestPassByVacanciesAndUser(user, vacancies);
        for (UserTestPass userTestPass : userTestPasses) {
            var testPassed = hashMap.get(userTestPass.getTest().getVacancy().getId());
            if (testPassed == null) {
                testPassed = new ArrayList<>();
                hashMap.put(userTestPass.getTest().getVacancy().getId(), testPassed);
            }
            testPassed.add(userTestPass.getTest().getId());
        }

        List<UserTestPassedDto> userTestPassedDtos = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entry : hashMap.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            var userTestPassedDto = new UserTestPassedDto();
            userTestPassedDto.setVacancy(key);
            userTestPassedDto.setTestPassed(value);
            userTestPassedDtos.add(userTestPassedDto);
        }
        return userTestPassedDtos;
    }


    public UserMeDto getById(Long id) {
        var user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        var dto = userMapper.userMeDtoFromUser(user);
        return dto;
    }
}
