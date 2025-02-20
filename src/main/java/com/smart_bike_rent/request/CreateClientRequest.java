package com.smart_bike_rent.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateClientRequest {


    private String firstName;
    private String lastName;
    private String identityCard;
    private Integer phoneNumber;

}
