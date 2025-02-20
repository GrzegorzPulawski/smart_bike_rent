package com.smart_bike_rent.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentingRequest {
    private Long idClient;
    private List<Long> idEquipment;

}
