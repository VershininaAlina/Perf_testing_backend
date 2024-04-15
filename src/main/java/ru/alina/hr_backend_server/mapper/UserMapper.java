package ru.alina.hr_backend_server.mapper;

import ru.alina.hr_backend_server.dto.user.UserDto;
import ru.alina.hr_backend_server.dto.user.UserMeDto;
import ru.alina.hr_backend_server.entity.user.User;

import java.util.List;

public interface UserMapper {
    UserDto userDtoFromUser(User user);
    List<UserDto> userDtoListFromUserList(List<User> users);

    UserMeDto userMeDtoFromUser(User user);
    List<UserMeDto> userMeDtoListFromUserList(List<User> users);
}
