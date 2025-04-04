package com.smart_bike_rent.service;

import com.smart_bike_rent.dto.EquipmentDTO;
import com.smart_bike_rent.entity.equipment.Equipment;
import com.smart_bike_rent.exception.EquipmentNotFoundException;
import com.smart_bike_rent.repositories.EquipmentRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentBarcodeService {
    private final EquipmentRepository equipmentRepository;

    public EquipmentBarcodeService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }


    @Transactional
    public String getBarcodeValue(Long bikeId) {
        Equipment bike = equipmentRepository.findById(bikeId)
                .orElseThrow(() -> new EquipmentNotFoundException(bikeId));

        if (bike.getBarcodeValue() == null) {
            bike.setBarcodeValue(generateBarcode(bikeId));
            equipmentRepository.save(bike);
        }
        return bike.getBarcodeValue();
    }

    private String generateBarcode(Long bikeId) {
        String randomSuffix = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
        return String.format("BIKE-%03d-%s", bikeId, randomSuffix); // np. "BIKE-042-AB3D"
    }
    public EquipmentDTO saveBarcode(EquipmentDTO equipmentDTO){
        if (equipmentDTO == null || equipmentDTO.getBarcodeValue().isEmpty()){
            String barcode = generateBarcode(equipmentDTO.getIdEquipment());
            equipmentDTO.setBarcodeValue(barcode);
        }
        return  equipmentRepository.save(equipmentDTO.map)
    }

}