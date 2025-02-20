package com.smart_bike_rent.security.service;

import com.smart_bike_rent.security.dto.SignUpDto;
import com.smart_bike_rent.security.dto.UserDto;
import com.smart_bike_rent.security.entity.User;
import com.smart_bike_rent.security.userrole.Role;
import org.springframework.stereotype.Service;

@Service
public class CustomUserMapper {

    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setRole(user.getRole());
        userDto.setCalendar(user.isCalendar());
        return userDto;
    }

    public User signUpToUser (SignUpDto signUpDto) {
        if (signUpDto == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setLogin(signUpDto.getLogin());
        user.setPassword(signUpDto.getPassword());
        user.setRole(Role.USER);
        //user.setCalendar();
        return user;
    }
}
