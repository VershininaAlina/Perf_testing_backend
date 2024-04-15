package ru.alina.hr_backend_server.controller.authentication;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.alina.hr_backend_server.dto.auth.UserAuthDto;
import ru.alina.hr_backend_server.dto.auth.UserAuthenticationDto;
import ru.alina.hr_backend_server.dto.auth.UserRegDto;
import ru.alina.hr_backend_server.dto.auth.UserRegistrationDto;
import ru.alina.hr_backend_server.entity.profile.Profile;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.exception.AuthenticationException;
import ru.alina.hr_backend_server.exception.RegistrationException;
import ru.alina.hr_backend_server.repository.ProfileRepository;
import ru.alina.hr_backend_server.repository.UserRepository;
import ru.alina.hr_backend_server.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private final JwtService jwtService;


    public UserAuthDto signIn(UserAuthenticationDto userAuthenticationDto) {

        logger.info("Starting user authentication process with email: {}", userAuthenticationDto.getEmail());

        var user = userRepository.getUserByEmailAndPassword(userAuthenticationDto.getEmail(), userAuthenticationDto.getPassword());

        if (user == null) {

            logger.error("User authentication failed for email: {}", userAuthenticationDto.getEmail());

            throw new AuthenticationException("Authorization failed");

        }

        logger.info("User authentication successful for email: {}", userAuthenticationDto.getEmail());

        return getUserAuthDto(user);

    }


    @Transactional
    public UserAuthDto singUp(UserRegistrationDto userRegistrationDto) {

        logger.info("Starting user registration process with email: {}", userRegistrationDto.getEmail());

        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            logger.error("User registration failed for email: {}", userRegistrationDto.getEmail());
            throw new RegistrationException("Email is already in use");
        }

        var profile = new Profile(userRegistrationDto.getName(), userRegistrationDto.getLastname(), userRegistrationDto.getSecondname(), userRegistrationDto.getBirthDay(), null);
        profileRepository.save(profile);
        var user = new User(userRegistrationDto.getEmail(), userRegistrationDto.getPassword(), profile);
        userRepository.save(user);


        logger.info("User registration successful for email: {}", user.getEmail());

        return getUserAuthDto(user);

    }

    private UserAuthDto getUserAuthDto(User user) {
        var token = jwtService.generateToken(user);
        return new UserAuthDto(token);
    }


}
