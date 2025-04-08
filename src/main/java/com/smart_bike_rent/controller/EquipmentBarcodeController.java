package com.smart_bike_rent.controller;

import com.smart_bike_rent.dto.EquipmentDTO;
import com.smart_bike_rent.exception.EquipmentNotExists;
import com.smart_bike_rent.service.EquipmentBarcodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipments/barcode")
public class EquipmentBarcodeController {
    private final EquipmentBarcodeService barcodeService;

    public EquipmentBarcodeController(EquipmentBarcodeService barcodeService) {
        this.barcodeService = barcodeService;
    }


    @PostMapping("/{bikeId}/generate")
    public ResponseEntity<String> generateBarcode(@PathVariable Long bikeId) {
        String barcode = barcodeService.getBarcodeValue(bikeId);
        return ResponseEntity.ok(barcode);
    }

    @GetMapping("/{bikeId}")
    public ResponseEntity<String> getBarcode(@PathVariable Long bikeId) {
        String barcode = barcodeService.getBarcodeValue(bikeId);
        return ResponseEntity.ok(barcode);
    }

    @GetMapping("/find/{barcode}")
    public ResponseEntity<?> getEquipmentByBarCode(@PathVariable String barcode) {
        try {
            EquipmentDTO found = barcodeService.findEquipmentByCodeBar(barcode);
            return ResponseEntity.ok(found);
        } catch (
                EquipmentNotExists e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if not found
        }
    }
}