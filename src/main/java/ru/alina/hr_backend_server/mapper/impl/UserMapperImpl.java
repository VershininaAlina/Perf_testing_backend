package ru.alina.hr_backend_server.mapper.impl;

import ru.alina.hr_backend_server.dto.user.UserDto;
import ru.alina.hr_backend_server.dto.user.UserMeDto;
import ru.alina.hr_backend_server.entity.user.User;
import ru.alina.hr_backend_server.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getProfile().getName());
        userDto.setLastname(user.getProfile().getLastname());
        userDto.setSecondname(user.getProfile().getSecondname());
        userDto.setExpiriences(user.getProfile().getExpiriences());
        userDto.setPhotoProfile(user.getProfile().getPhotoProfile());
        userDto.setRole(user.getRole());
        userDto.setBirthDay(user.getProfile().getBirthDay());
        return userDto;
    }

    @Override
    public List<UserDto> userDtoListFromUserList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(userDtoFromUser(user));
        }
        return userDtos;
    }

    @Override
    public UserMeDto userMeDtoFromUser(User user) {
        UserMeDto userDto = new UserMeDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getProfile().getName());
        userDto.setLastname(user.getProfile().getLastname());
        userDto.setSecondname(user.getProfile().getSecondname());
        userDto.setExpiriences(user.getProfile().getExpiriences());
        userDto.setPhotoProfile(user.getProfile().getPhotoProfile());
        userDto.setRole(user.getRole());
        userDto.setBirthDay(user.getProfile().getBirthDay());
        return userDto;
    }

    @Override
    public List<UserMeDto> userMeDtoListFromUserList(List<User> users) {
        List<UserMeDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(userMeDtoFromUser(user));
        }
        return userDtos;
    }

}
