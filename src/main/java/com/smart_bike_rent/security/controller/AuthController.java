package com.smart_bike_rent.security.controller;

import com.smart_bike_rent.security.dto.CredentialsDto;
import com.smart_bike_rent.security.dto.SignUpDto;
import com.smart_bike_rent.security.service.UserAuthProvider;
import com.smart_bike_rent.security.dto.UserDto;
import com.smart_bike_rent.security.service.UserServiceNew;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserServiceNew userServiceNew;
    private  final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userServiceNew.login(credentialsDto);

        user.setToken(userAuthProvider.createToken(user.getLogin()));
        return ResponseEntity.ok(user);
    }
    @PreAuthorize("hasAuthority('DEVEL')")
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto) {
        UserDto user = userServiceNew.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user.getLogin()));
        return ResponseEntity.created(URI.create("/user/"+ user.getId()))
                .body(user);
    }

}
