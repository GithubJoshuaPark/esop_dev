package com.soro.esop.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.soro.esop.dto.UserDto;
import com.soro.esop.entiry.User;

@Component
public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEnabled(user.getEnabled());
        return userDto;
    }

    public static List<UserDto> toDto(List<User> users) {
        if(users == null) {
            return null;
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(toDto(user));
        }
        return userDtos;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        return user;
    }

    public static List<User> toEntity(List<UserDto> userDtos) {
        if(userDtos == null) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (UserDto userDto : userDtos) {
            users.add(toEntity(userDto));
        }
        return users;
    }
}
