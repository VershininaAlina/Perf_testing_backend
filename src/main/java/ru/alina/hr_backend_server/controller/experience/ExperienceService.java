package ru.alina.hr_backend_server.controller.experience;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.dto.experience.ExperienceDto;
import ru.alina.hr_backend_server.entity.profile.Experience;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.repository.ExperienceRepository;
import ru.alina.hr_backend_server.repository.ProfileRepository;
import ru.alina.hr_backend_server.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ExperienceRepository experienceRepository;


    public void addExperience(Authentication authentication, ExperienceDto experienceDto) {
        var user = userRepository.findUserByEmail(authentication.getName()).get();

        var experience = new Experience(experienceDto.getPosition(), experienceDto.getYearOfExperience(), experienceDto.getDescription(), user);
        experienceRepository.save(experience);

        user.getProfile().getExpiriences().add(experience);
        profileRepository.save(user.getProfile());
    }

    public void editExperience(Authentication authentication, ExperienceDto experienceDto, Long id) {
        var user = userRepository.findUserByEmail(authentication.getName()).get();
        var experience = experienceRepository.getExperienceByIdAndUser(id, user);
        if (experience == null) {
            throw new NotFoundException("Experience is null");
        }
        experience.setPosition(experienceDto.getPosition());
        experience.setYearOfExpirience(experienceDto.getYearOfExperience());
        experience.setDescription(experienceDto.getDescription());
        experienceRepository.save(experience);
    }

    public void deleteExperience(Authentication authentication, Long id) {
        var user = userRepository.findUserByEmail(authentication.getName()).get();

        var experience = experienceRepository.getExperienceByIdAndUser(id, user);
        if (experience == null) {
            throw new NotFoundException("Not found experience");
        }
        for (Experience experienceTarget : user.getProfile().getExpiriences()) {
            if (experienceTarget.getId() == experience.getId()) {
                user.getProfile().getExpiriences().remove(experienceTarget);
                profileRepository.save(user.getProfile());
                break;
            }
        }
        experienceRepository.delete(experience);
    }
}
