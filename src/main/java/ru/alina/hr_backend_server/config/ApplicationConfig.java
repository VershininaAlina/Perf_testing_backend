package ru.alina.hr_backend_server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.alina.hr_backend_server.mapper.*;
import ru.alina.hr_backend_server.mapper.impl.*;
import ru.alina.hr_backend_server.repository.UserRepository;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;


    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public AnswerMapper answerMapper() {
        return new AnswerMapperImpl();
    }

    @Bean
    public QuestionMapper questionMapper() {
        return new QuestionMapperImpl(answerMapper());
    }

    @Bean
    public TestMapper testMapper() {
        return new TestMapperImpl(questionMapper());
    }

    @Bean
    public VacancyMapper vacancyMapper() {
        return new VacancyMapperImpl(testMapper(), userMapper());
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST", "GET", "OPTIONS", "DELETE");
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found"));

    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
