package com.smart_bike_rent.security.controller;

import com.smart_bike_rent.security.dto.UserDetailsDto;
import com.smart_bike_rent.security.dto.UserDto;
import com.smart_bike_rent.security.service.UserAuthProvider;
import com.smart_bike_rent.security.service.UserServiceNew;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceNew userService;
    private final UserAuthProvider userAuthProvider;
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/details")
    public ResponseEntity<UserDetailsDto> getUserDetails(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        Long userId = userAuthProvider.extractUserId(token);  // Assume your JWT has a method to get `userId`

        UserDto user = userService.getUserById(userId);

        UserDetailsDto userDetails = new UserDetailsDto();
        //userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        userDetails.setCalendar(user.isCalendar());

        return ResponseEntity.ok(userDetails);
    }
    @PreAuthorize("hasAuthority('DEVEL')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
        { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
        List <UserDto> userDtoList = userService.getAllUsers();
        log.info("List of all users");
        return ResponseEntity.ok(userDtoList);

    }
}
