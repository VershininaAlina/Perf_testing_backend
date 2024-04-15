package ru.alina.hr_backend_server.controller.admin.hr;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.controller.authentication.AuthenticationService;
import ru.alina.hr_backend_server.dto.auth.UserAuthDto;
import ru.alina.hr_backend_server.dto.auth.UserRegistrationDto;
import ru.alina.hr_backend_server.dto.user.UserDto;
import ru.alina.hr_backend_server.entity.profile.Profile;
import ru.alina.hr_backend_server.entity.user.Role;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.exception.NotFoundException;
import ru.alina.hr_backend_server.exception.RegistrationException;
import ru.alina.hr_backend_server.mapper.UserMapper;
import ru.alina.hr_backend_server.repository.ProfileRepository;
import ru.alina.hr_backend_server.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HrService {
    private static final Logger logger = LoggerFactory.getLogger(HrService.class);

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private final UserMapper userMapper;


    @Transactional
    public void singUp(UserRegistrationDto userRegistrationDto) {

        logger.info("Starting hr registration process with email: {}", userRegistrationDto.getEmail());

        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            logger.error("HR registration failed for email: {}", userRegistrationDto.getEmail());
            throw new RegistrationException("Email is already in use");
        }

        var profile = new Profile(userRegistrationDto.getName(), userRegistrationDto.getLastname(), userRegistrationDto.getSecondname(), userRegistrationDto.getBirthDay(), null);
        profileRepository.save(profile);
        var user = new User(userRegistrationDto.getEmail(), userRegistrationDto.getPassword(), profile);
        user.setRole(Role.HR);
        userRepository.save(user);


        logger.info("HR registration successful for email: {}", user.getEmail());


    }

    public List<UserDto> getUserHr() {
        var users = userRepository.getUserByRole(Role.HR);
        return userMapper.userDtoListFromUserList(users);
    }


    public void setUserRole(Long id, Role role) {
        var user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        user.setRole(role);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        var user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        ;
        userRepository.delete(user);
    }
}
