package com.smart_bike_rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentingDTO {
    private Long idRenting;
    private String firstName;
    private String lastName;
    private String identityCard;
    private Integer phoneNumber;

    private String dateRenting;
    private String nameEquipment;
    private Double priceEquipment;
    private String frameNumber;

    private String dateOfReturn;
    private Double priceOfDuration;
    private Long daysOfRental;

}
