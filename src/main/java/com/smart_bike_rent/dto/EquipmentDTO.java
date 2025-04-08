package com.smart_bike_rent.dto;

import com.smart_bike_rent.entity.equipment.Equipment;
import com.smart_bike_rent.entity.equipment.SizeBike;
import com.smart_bike_rent.entity.equipment.TypeBike;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
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
    private String barcodeValue;

    public static Equipment mapDTOToEquipment(EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setIdEquipment(dto.getIdEquipment());
        equipment.setNameEquipment(dto.getNameEquipment());
        equipment.setFrameNumber(dto.getFrameNumber());
        equipment.setSize(dto.getSize());
        equipment.setType(dto.getType());
        equipment.setAvailable(dto.isAvailable());
        equipment.setElectric(dto.isElectric());
        equipment.setPriceEquipment(dto.getPriceEquipment());
        equipment.setBarcodeValue(dto.getBarcodeValue());

        return equipment;
    }
}
