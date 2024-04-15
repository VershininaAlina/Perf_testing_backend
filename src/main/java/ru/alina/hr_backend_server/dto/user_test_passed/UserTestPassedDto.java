package ru.alina.hr_backend_server.dto.user_test_passed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTestPassedDto {
    private Long vacancy;
    private List<Long> testPassed;
}
