package com.smart_bike_rent.dto;

import com.smart_bike_rent.entity.equipment.SizeBike;
import com.smart_bike_rent.entity.equipment.TypeBike;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {
    private Long idEquipment;

    private String nameEquipment;
    private String frameNumber;
    private SizeBike size;
    private TypeBike type;
    private boolean available;
    private boolean electric;
    private Double priceEquipment;
}
