package com.smart_bike_rent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {
    private Long idEquipment;

    private String nameEquipment;
    private Double priceEquipment;
}
