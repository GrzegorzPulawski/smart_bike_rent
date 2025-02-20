package com.smart_bike_rent.security.service;

import com.smart_bike_rent.security.entity.User;
import com.smart_bike_rent.security.exception.AppException;
import com.smart_bike_rent.security.dto.CredentialsDto;
import com.smart_bike_rent.security.dto.SignUpDto;
import com.smart_bike_rent.security.dto.UserDto;
import com.smart_bike_rent.security.userrole.Role;
import com.smart_bike_rent.security.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class UserServiceNew {
    private final UserRepository userRepository;
    private final CustomUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Nie znaleziono loginu", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Nieznany uzytkownik", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Złe hasło", HttpStatus.BAD_REQUEST);
    }

      public UserDto register(SignUpDto userDto){
          Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());
       if(optionalUser.isPresent()){
            throw  new AppException("Login already exists", HttpStatus.BAD_REQUEST);
       }
        User user = userMapper.signUpToUser(userDto);
         user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
      User savedUser = userRepository.save(user);
       return userMapper.toUserDto(user);
     }
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        return userMapper.toUserDto(user); // Map User to UserDTO
    }
    public List<UserDto> getAllUsers() {
        List<User> userlist = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userlist) {
            userDtoList.add(userMapper.toUserDto(user));
        }
        return userDtoList;
    }
}
