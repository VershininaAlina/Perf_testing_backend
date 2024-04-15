package ru.alina.hr_backend_server.dto.user_test_passed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alina.hr_backend_server.dto.test.TestDto;
import ru.alina.hr_backend_server.dto.video.VideoDto;
import ru.alina.hr_backend_server.entity.video.Video;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVacancyTestPassed {
    private TestDto testDto;
    private Integer numberOfCorrectAnswer;
    private Integer numberOfIncorrect;
    private VideoDto video;

}
