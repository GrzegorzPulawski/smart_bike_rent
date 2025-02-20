package com.smart_bike_rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long idClient;
    private String firstName;
    private String lastName;
    private String identityCard;
    private Integer phoneNumber;
}
