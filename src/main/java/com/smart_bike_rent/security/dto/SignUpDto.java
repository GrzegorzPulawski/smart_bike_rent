package com.smart_bike_rent.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class  SignUpDto {
    private String firstName;
    private String lastName;
    private String login;
    private String password;

}
