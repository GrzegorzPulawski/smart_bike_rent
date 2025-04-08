package com.smart_bike_rent.service;

import com.smart_bike_rent.dto.EquipmentDTO;
import com.smart_bike_rent.entity.equipment.Equipment;
import com.smart_bike_rent.exception.EquipmentNotFoundException;
import com.smart_bike_rent.repositories.EquipmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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


    public EquipmentDTO  findEquipmentByCodeBar(String barcode) {
        Optional<Equipment> equipmentOptional = equipmentRepository.findByBarcodeValue(barcode);
        if (equipmentOptional.isPresent()) {
            Equipment equipment =equipmentOptional.get();
            EquipmentDTO equipmentDTO = equipment.mapEquipmentToDTO();
            return equipmentDTO;
        }
        throw new RuntimeException("Nie zanleziono roweru z podanym kodem");

    }

}