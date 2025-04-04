package com.smart_bike_rent.controller;

import com.smart_bike_rent.service.EquipmentBarcodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipment/barcode")
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

}