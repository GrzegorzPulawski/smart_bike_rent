package com.smart_bike_rent.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEquipmentRequest {

    private String nameEquipment;
    private Double priceEquipment;
}
