package com.smart_bike_rent.security.dto;

import com.smart_bike_rent.security.userrole.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private  String login;
    private  String token;
    private Role role;
    private boolean calendar;
}
