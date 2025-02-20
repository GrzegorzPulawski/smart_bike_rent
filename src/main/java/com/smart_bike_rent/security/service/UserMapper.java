package com.smart_bike_rent.security.service;

import com.smart_bike_rent.security.dto.SignUpDto;
import com.smart_bike_rent.security.dto.UserDto;
import com.smart_bike_rent.security.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(ignore = true, target = "password" )
    User signUpToUser(SignUpDto userDto);
}
