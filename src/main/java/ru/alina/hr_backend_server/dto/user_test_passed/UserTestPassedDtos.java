package ru.alina.hr_backend_server.dto.user_test_passed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.dto.user.UserDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestPassedDtos {
    private UserDto userDto;
    private List<UserVacancyTestPassed> userVacancyTestPasseds;
}
